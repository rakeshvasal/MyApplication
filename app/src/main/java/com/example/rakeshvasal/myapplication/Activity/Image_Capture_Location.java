package com.example.rakeshvasal.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Custom_Adapters.Image_Adapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Image_Capture_Location extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    double latitude, longitude;
    final int CAMERA_CAPTURE = 1;
    private List<String> listOfImagesPath;
    private RecyclerView recyclerView;
    private Image_Adapter adapter;
    private List<Image_Items> albumList;
    //String ImagePath = Environment.getExternalStorageDirectory() + "/MyApplication/Image_Capture_Location/Pictures/";
    String ImagePath = Environment.getExternalStorageDirectory() + "/ImageFolder/";
    private static final int CAMERA_CUSTOM_CAPTURE = 1;
    SharedPreferences preferences;

    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;

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
        preferences = getSharedPreferences("userLocation", Context.MODE_PRIVATE);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("ImageList");

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
            public void onClick(View view) {
                Intent intent = new Intent(Image_Capture_Location.this, CameraActivity.class);
                startActivityForResult(intent, CAMERA_CUSTOM_CAPTURE);
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

        if (requestCode == CAMERA_CUSTOM_CAPTURE) {

            if (resultCode == RESULT_OK) {

                latitude  = Float.parseFloat(preferences.getString("userlat", "0.0"));
                longitude = Float.parseFloat(preferences.getString("userlng", "0.0"));
                String image_name,image_path,timeStamp1="";
                if (data != null) {
                    image_name = data.getStringExtra("imagename");
                    image_path = data.getStringExtra("image_path");
                }else{
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());
                    timeStamp1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss")
                            .format(new Date());
                   image_name = timeStamp + ".jpg";
                }

                Utils.Images_latitude_Array_List.add(latitude);
                Utils.Images_longitude_Array_List.add(longitude);
                Utils.Images_name_Array_List.add(image_name);
                Utils.images_time_arraylist.add(timeStamp1);
                AddtoFirebase(latitude,longitude,image_name,timeStamp1);

                listOfImagesPath = null;
                listOfImagesPath = new Utils().RetriveCapturedImagePath(ImagePath);
                if (listOfImagesPath != null) {
                adapter = new Image_Adapter(this,listOfImagesPath);
                recyclerView.setAdapter(adapter);
                }
            }
            else {
                shortToast("Error while Image Capture See Logs");
            }
        }
    }

    private void AddtoFirebase(Double lat,Double longt,String name,String time){

        try {
            Image_Items events = new Image_Items(name, time, lat, longt);

            String userId = mDatabase.push().getKey();

            mDatabase.child(userId).setValue(events);

        } catch (Exception e) {
            e.printStackTrace();
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


