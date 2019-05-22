package com.example.rakeshvasal.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Custom_Adapters.Image_Adapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Image_Items;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.URLModifiers;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image_Capture_Location extends BaseActivity implements URLModifiers.getValue {

    double latitude, longitude;

    private List<String> listOfImagesPath;
    private RecyclerView recyclerView;
    private Image_Adapter imageAdapter;
    ArrayList<Image_Items> mImageEntries = new ArrayList<>();
    String ImagePath = Environment.getExternalStorageDirectory() + "/ImageFolder/";
    private static final int CAMERA_CUSTOM_CAPTURE = 1;
    SharedPreferences preferences;
    TextView count;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    FirebaseStorage storage;
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__capture__location);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("userLocation", Context.MODE_PRIVATE);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("ImageList");

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        recyclerView = (RecyclerView) findViewById(R.id.image_list);
        listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);

        imageAdapter = new Image_Adapter(Image_Capture_Location.this, mImageEntries);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(imageAdapter);

        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText(R.string.Dashboard);
        count = (TextView) findViewById(R.id.image_count);

        ImageView click = (ImageView) findViewById(R.id.click);

        Utils.Images_name_Array_List.clear();
        Utils.Images_latitude_Array_List.clear();
        Utils.Images_longitude_Array_List.clear();
        Utils.Images_time_arraylist.clear();
        Utils.Images_url_Array_List.clear();

        FetchAllImageDetails();

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isConnectedToGps(Image_Capture_Location.this)) {
                    Intent intent = new Intent(Image_Capture_Location.this, CameraActivity.class);
                    startActivityForResult(intent, CAMERA_CUSTOM_CAPTURE);
                } else {
                    Utils.showSettingsAlert(Image_Capture_Location.this);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                try {
                    //Utils.openSourceFile(Device_Info.this, "Device_Info", "java");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CUSTOM_CAPTURE) {

            if (resultCode == RESULT_OK) {

                latitude = Float.parseFloat(preferences.getString("userlat", "0.0"));
                longitude = Float.parseFloat(preferences.getString("userlng", "0.0"));
                String image_name, image_path, timeStamp1 = "";
                if (data != null) {
                    image_name = data.getStringExtra("imagename");
                    image_path = data.getStringExtra("image_path");
                    timeStamp1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss")
                            .format(new Date());
                } else {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());
                    timeStamp1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss")
                            .format(new Date());
                    image_name = timeStamp + ".jpg";
                }

                Utils.Images_latitude_Array_List.add(latitude);
                Utils.Images_longitude_Array_List.add(longitude);
                Utils.Images_name_Array_List.add(image_name);
                Utils.Images_time_arraylist.add(timeStamp1);
                AddtoFirebase(latitude, longitude, image_name, timeStamp1);

                listOfImagesPath = null;
                listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);
            } else {
                shortToast("Error while Image Capture See Logs");
            }
        }
    }

    private void AddtoFirebase(final Double lat, final Double longt, final String name, final String time) {

        showProgressDialog("Uploading Image Please Wait");
        //used to store in the folder that u define directory
        final StorageReference ImagesRef = storageRef.child("images/" + name);
        listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);
        int size = listOfImagesPath.size();
        String path = listOfImagesPath.get(size - 1);
        File imageFile = new File(path);
        try {
            UploadTask uploadTask = ImagesRef.putFile(Uri.fromFile(imageFile));
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        closeProgressDialog();
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ImagesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        closeProgressDialog();
                        String downloadUri = task.getResult().toString();
                        Image_Items image_items = new Image_Items(name, time, downloadUri, lat, longt, true);
                        String userId = mDatabase.push().getKey();
                        mDatabase.child(userId).setValue(image_items);
                    } else {
                        closeProgressDialog();
                        Image_Items image_items = new Image_Items(name, time, "", lat, longt, false);
                        String userId = mDatabase.push().getKey();
                        mDatabase.child(userId).setValue(image_items);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            shortToast("Exception while Uploading");
            closeProgressDialog();
        }


    }

    private void FetchAllImageDetails() {
        showProgressDialog();
        mImageEntries.clear();
        try {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        //closeProgressDialog();
                        Image_Items image_items = eventsnapshot.getValue(Image_Items.class);
                        String key = eventsnapshot.getKey();
                        Utils.Images_name_Array_List.add(image_items.getName());
                        Utils.Images_latitude_Array_List.add(image_items.getLatitude());
                        Utils.Images_longitude_Array_List.add(image_items.getLongitude());
                        Utils.Images_time_arraylist.add(image_items.getTime());
                        String img_url = image_items.getDownload_url();
                        if (img_url == null || img_url.equalsIgnoreCase(" ")) {
                            Utils.Images_url_Array_List.add("");
                        } else {
                            Utils.Images_url_Array_List.add(image_items.getDownload_url());
                        }

                        mImageEntries.add(image_items);

                    }
                    int size = mImageEntries.size();
                    count.setText("Total Images :" + size);
                    closeProgressDialog();
                    imageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    closeProgressDialog();
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                    shortToast("Exception");
                }
            });


        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }
    }

    @Override
    public void setData(String data) {
        logInfo("img_shot_links " + data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Image_Capture_Location.this, Dashboard.class);
        startActivity(intent);
    }
}


