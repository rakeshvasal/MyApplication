package com.example.rakeshvasal.myapplication.Utilities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Activity.Dashboard;
import com.example.rakeshvasal.myapplication.Activity.MainActivity;
import com.example.rakeshvasal.myapplication.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
    public static ArrayList<String> Images_time_arraylist = new ArrayList<>();
    public static ArrayList<String> Images_url_Array_List = new ArrayList<>();
    public static ArrayList<String> eventselected = new ArrayList<>();
    public static ArrayList<String> eventselectedamounts = new ArrayList<>();
    public static ArrayList<String> step2_spinner_position_database_insert_update_value = new ArrayList<>();
    public static ArrayList<String> step2_photo_category_id = new ArrayList<>();
    public static ArrayList<String> json_city_id = new ArrayList<>();
    public static ArrayList<String> vehicle_model_name_array_list = new ArrayList<>();
    public static ArrayList<String> json_model_id = new ArrayList<>();
    public static ArrayList<String> chk_value_lead_id = new ArrayList<>();
    public static String API_KEY = "";
    public static String MOVIEDB_BASE_URL = "https://api.themoviedb.org/3/";
        public static String CRIC_INFO_BASE_URL = "http://cricapi.com/api/";
    public static String MOVIEDB_PIC_BASE_URL = "https://image.tmdb.org/t/p/w780";
    public static String ADD_TASK = "ADD";
    public static String UPDATE_TASK = "UPDATE";
    public static String TASK = "TASK";
    private static ProgressDialog progressDialog;
    public static String GOOGLE_LOGIN_DATA = "GoogleAccountDetails";
    public static String Imagga_API_URL = "https://api.imagga.com";

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

    public static String getSystemTime() {
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy")
                .format(new Date());
        return timeStamp;
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

    public static void openSourceFile(Activity activity, String filename, String filetype) {
        //File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        Uri uri = Uri.parse("file:///android_asset/" + filetype + "/" + filename + ".txt");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        activity.startActivity(intent);
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

    public static void showSettingsAlert(final Context activity) {
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

    private String ListtoJson(List<Object> list) {

        String json = new Gson().toJson(list);

        return json;

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

        //If a activity_facebook container, iterate over children and seed recursion.
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

    public static void updateUI(Activity activity, boolean signedIn) {
        if (signedIn) {
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Intent intent = new Intent(activity, Dashboard.class);
            activity.startActivity(intent);
            activity.finish();
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for (int idx = 0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    public static byte[] getUTF8Bytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (Exception ex) {
            return null;
        }
    }

    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN = 1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8 = false;
            int read, count = 0;
            while ((read = is.read(bytes)) != -1) {
                if (count == 0 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
                    isUTF8 = true;
                    baos.write(bytes, 3, read - 3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count += read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx = 0; idx < mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length() > 0) buf.deleteCharAt(buf.length() - 1);
                return buf.toString();
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";

    }


    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static void showProgressDialog(Activity activity, String msg) {
        progressDialog = ProgressDialog.show(activity, "Please wait", msg,
                true, true);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public static void closeProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}


