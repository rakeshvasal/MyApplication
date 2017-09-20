package com.example.rakeshvasal.myapplication.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Custom_Adapters.Image_Adapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Image_Items;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Services.UserLocation;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image_Capture_Location extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    double latitude, longitude;
    final int CAMERA_CAPTURE = 1;
    private List<String> listOfImagesPath;
    private RecyclerView recyclerView;
    private Image_Adapter adapter;
    private List<Image_Items> albumList;
    String ImagePath = Environment.getExternalStorageDirectory() + "/MyApplication/Image_Capture_Location/Pictures/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__capture__location);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        recyclerView = (RecyclerView) findViewById(R.id.image_list);
        listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);

        //albumList = new ArrayList<>();
        adapter = new Image_Adapter(this, listOfImagesPath);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);



        TextView page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText(R.string.Dashboard);
        ImageView sign_out = (ImageView) findViewById(R.id.sign_out);
        ImageView click = (ImageView) findViewById(R.id.click);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnectedToGps(Image_Capture_Location.this)) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(Image_Capture_Location.this.getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = Utils.createImageFile(Image_Capture_Location.this, "Image_Capture_Location");
                            // GridViewDemo_ImagePath=photoFile.getPath();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Toast.makeText(Image_Capture_Location.this, "Catched No Directory", Toast.LENGTH_SHORT).show();

                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile));
                            startActivityForResult(takePictureIntent, CAMERA_CAPTURE);
                        } else {
                            Toast.makeText(Image_Capture_Location.this, "No Directory", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Image_Capture_Location.this, "Take picture intent null", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Utils.showSettingsAlert(Image_Capture_Location.this);
                }
            }
        });
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });


    }

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // [END signOut]
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Image_Capture_Location.this.RESULT_OK) {
            //user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                long HeapSize = Runtime.getRuntime().maxMemory();

                listOfImagesPath = null;
                listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);
                int size = listOfImagesPath.size();
                /*for (int i=0;i<=size;i++){
                    Utils.images_path_arraylist.add(listOfImagesPath.get(i));
                }*/
                if (listOfImagesPath != null) {
                    try {
                        UserLocation loc = new UserLocation(Image_Capture_Location.this);
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();

                        Utils.Images_latitude_Array_List.add(latitude);
                        Utils.Images_longitude_Array_List.add(longitude);
                        String name =(listOfImagesPath.get(listOfImagesPath.size() - 1)).toString();
                        String image_name = name.replace(ImagePath,"");
                        Utils.Images_name_Array_List.add(image_name);
                        adapter = new Image_Adapter(this,listOfImagesPath);
                        recyclerView.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent =new Intent(Image_Capture_Location.this,Dashboard.class);
        startActivity(intent);
    }
}


