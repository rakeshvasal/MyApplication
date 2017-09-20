package com.example.rakeshvasal.myapplication.Utilities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rakeshvasal on 31-Dec-16.
 */

public class Utils {

    static SharedPreferences pref;
    public static ArrayList<Double> Images_latitude_Array_List = new ArrayList<>();
    public static ArrayList<Double> Images_longitude_Array_List = new ArrayList<>();
    public static ArrayList<String> Images_name_Array_List = new ArrayList<>();
    public static ArrayList<String> images_path_arraylist = new ArrayList<>();
    public static ArrayList<String> vehicle_make_id_array_list = new ArrayList<>();
    public static ArrayList<String> photos_spinner_update_value = new ArrayList<>();
    public static ArrayList<String> step2_spinner_position_update_value = new ArrayList<>();
    public static ArrayList<String> step2_spinner_position_database_insert_update_value = new ArrayList<>();
    public static ArrayList<String> step2_photo_category_id = new ArrayList<>();
    public static ArrayList<String> json_city_id = new ArrayList<>();
    public static ArrayList<String> vehicle_model_name_array_list = new ArrayList<>();
    public static ArrayList<String> json_model_id = new ArrayList<>();
    public static ArrayList<String> chk_value_lead_id = new ArrayList<>();
    public static ArrayList<String> positionOfDeletion = new ArrayList<>();


    public static boolean isLoggedIn(Context context) {
        pref = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        return pref.getBoolean("isLoggedIn", false);
    }

    public static void logOut(Context context) {
        pref = context.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        pref.edit().putBoolean("isLoggedIn", false).apply();
    }


    public static boolean is_Connected_To_Internet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {

                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // To retrieve images list from folder
    public List<String> RetriveCapturedImagePath(String GridViewDemo_ImagePath) {

        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files = f.listFiles();
            if (files != null) {
                Arrays.sort(files);


                for (File file : files) {
                    if (file.isDirectory())
                        continue;
                    tFileList.add(file.getPath());
                }
            }
        }
        return tFileList;
    }

    /* function to check if provided @editText is null then set the error message @errorMessage else */
    public void editTextError(EditText editText, String errorMessage) {
        if (editText.getText().length() <= 0) {
            editText.setError(errorMessage);
        } else {
            editText.setError(null);
        }
    }

    public static File createImageFile(Activity activity, String ActivityName) throws IOException {
        String mCurrentPhotoPath;
        // Create an image file name
        String imageFileName = "";// id+"~Photo.jpg";//"JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/MyApplication/" + ActivityName + "/Pictures/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        String directory = storageDir.toString();
        for (int i = 1; i <= 100; i++) {
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            File file = new File(directory + "/" + ts + "-.jpg");
            if (file.exists()) {
                // do nothing
                // as the image already exists and if we do anything it might override it
                //so leave this condition as it is.
            } else {
                // if the given file name is not found then pass the first name that is encountered and do not exists.
                // and then break the loop as it useless to continue to do so.
                imageFileName = ts + "-.jpg";
                break;
            }
        }

        File image = new File(storageDir, imageFileName); /*File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );*/

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static boolean isServiceRunning(Activity activity, String serviceName) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isConnectedToGps(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void showSettingsAlert(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        // Setting Dialog Title
        alertDialog.setTitle("GPS  settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(activity, "Need GPS to work properly!", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }

    /**
     * This function works like whatsapp compression, Any image that is of size 1600*1200 or less is returned as it is,
     * else it will maintain the aspect ratio and rescale the image to the new aspect ratio.
     *
     * @param bmp Bitmap Image that needs to be rescaled.
     * @return Returns the rescaled Bitmap.
     */
    public static Bitmap scaleDownImage(Bitmap bmp) {

        float originalImageHeight = bmp.getHeight();
        float originalImageWidth = bmp.getWidth();

        /**  maxHeight and maxWidth is hardcode and can be improved in future.
         * */
        float maxHeight = 1200.0f;
        float maxWidth = 1600.0f;

        /**
         * Calculate the Ratio of original image and max image size.
         * */
        float originalRatio = originalImageWidth / originalImageHeight;
        float maxRatio = maxWidth / maxHeight;

        /**
         * first check if the original image is equal to or less than the max size specified.
         * */
        if (originalImageHeight > maxHeight || originalImageWidth > maxWidth) {

            if (originalRatio < maxRatio) {
                /***
                 *if the ratio of original image is less than the maxRatio then find the ration by dividing maxHeight by original height
                 * and then take the original ratio and multiply the width and assign the new height and width to original size.
                 *
                 * this code is executed when the height needs to be scaled to maintain the aspect ratio.
                 */
                originalRatio = maxHeight / originalImageHeight;

                originalImageWidth = originalRatio * originalImageWidth;

                originalImageHeight = maxHeight;
            } else if (originalRatio > maxRatio) {
                /**
                 * This code is executed when the width needs to be rescaled to maintain the aspect ratio.
                 * */
                originalRatio = maxWidth / originalImageWidth;
                originalImageHeight = originalRatio * originalImageHeight;
                originalImageWidth = maxWidth;

            } else {

                originalImageHeight = maxHeight;
                originalImageWidth = maxWidth;
            }
        }
        /**
         * Now return the rescaled image maintaining the aspect.
         * */
        return Bitmap.createScaledBitmap(bmp, (int) originalImageWidth, (int) originalImageHeight, false);
    }


    /**
     * Converts any given String ArrayList to Integer ArrayList
     *
     * @param stringArray String ArrayList
     * @return Return Equivalent Integer ArrayList.
     */
    public static ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (String stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.parseInt(stringValue));
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Converts any given Integer ArrayList to String ArrayList
     *
     * @param stringArray Integer ArrayList
     * @return Return Equivalent String ArrayList.
     */

    public static ArrayList<String> getStringArray(ArrayList<Integer> stringArray) {
        ArrayList<String> result = new ArrayList<String>();
        for (Integer stringValue : stringArray) {
            try {
                //Convert String to Integer, and store it into integer array list.
                result.add(Integer.toString(stringValue));
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return result;
    }

    public static String getFullAddress(double lat, double lnt, Activity activity) {
        String fullAddress = "";
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            String address = " Unable to find Address", city = "", state = "", country = "", zip = "";
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lnt, 1);
                if (addresses != null) {

                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    zip = addresses.get(0).getPostalCode();
                    country = addresses.get(0).getCountryName();
                }
            } catch (Exception e) {
                e.printStackTrace();
                fullAddress = "";
            }
            fullAddress = address + " " + city + " " + state + " " + country + " " + zip;
        } catch (Exception e) {
            fullAddress = "";

        }
        return fullAddress;
    }

    public static void setupUI(View view, final Activity activity) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView, activity);
            }
        }
    }


    public static String getMemoryAndCpuData(Activity activity) {

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        Float availableMegs = mi.availMem / 1048576f;// 1024*1204  converting from byte to megabyte.
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        float avilbaleStorage = (statFs.getAvailableBlocks() * statFs.getBlockSize()) / 1048576f;
        return availableMegs + " MB." + "\n Available Storage :" + avilbaleStorage + " MB.";


    }

    /**
     * Returns the value of specified {@code column} from the shared preference.
     *
     * @param activity       instance of the calling activity
     * @param sharedPrefName name of the shared preference
     * @param column         name of the field whose value you want
     * @return return a string of value from the  specified {@code column} or else blank
     */

    public static String getSharedPrefData(Activity activity, String sharedPrefName, String column) {

        SharedPreferences pref = activity.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        return pref.getString(column, "");


    }

    /**
     * Sets the specified  value of {@code value} into the {@code column}
     *
     * @param activity       instance of the calling activity
     * @param sharedPrefName name of the shared preference
     * @param column         name of the field whose value you set
     * @param value          value of the {@code column} you want to set
     */

    public static void setSharedPrefData(Activity activity, String sharedPrefName, String column, String value) {

        SharedPreferences pref = activity.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        pref.edit().putString(column, value).apply();


    }

    public static boolean FileExists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


