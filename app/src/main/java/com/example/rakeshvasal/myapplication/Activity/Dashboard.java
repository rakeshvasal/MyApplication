package com.example.rakeshvasal.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.ErrorHandlingClass;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Services.FusedLocationService;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof ErrorHandlingClass)) {
            Thread.setDefaultUncaughtExceptionHandler(new ErrorHandlingClass(this));
        }
        setContentView(R.layout.activity_dashboard);
        boolean serviceState = isMyServiceRunning(FusedLocationService.class);
        if (!serviceState) {
            Intent intent1 = new Intent(Dashboard.this, FusedLocationService.class);
            startService(intent1);
        }
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
        // Here, thisActivity is the current activity


        TextView page_title = findViewById(R.id.page_title);
        TextView nested_scroll_view = findViewById(R.id.nested_scroll_view);

        page_title.setText(R.string.Dashboard);
        ImageView sign_out = findViewById(R.id.sign_out);
        TextView photo_location = findViewById(R.id.image_capture);
        photo_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Image_Capture_Location.class);
                startActivity(intent);

            }
        });
        TextView location_search = findViewById(R.id.location);
        location_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnectedToGps(Dashboard.this)) {
                    Intent intent = new Intent(Dashboard.this, Search_Place_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Utils.showSettingsAlert(Dashboard.this);
                }
            }
        });
        TextView zomato = findViewById(R.id.zomato);
        zomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ZomatoActivity.class);
                startActivity(intent);

            }
        });

        TextView college_fest = findViewById(R.id.fest);
        college_fest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, OddessyMain.class);
                startActivity(intent);
            }
        });
        TextView Device_Info = findViewById(R.id.phone_info);
        Device_Info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Device_Info.class);
                startActivity(intent);
            }
        });
        TextView Facebook = findViewById(R.id.facebook);
        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FacebookActivity.class);
                startActivity(intent);
            }
        });

        TextView cricketAPI = findViewById(R.id.cricketAPI);
        cricketAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MatchDetailsActivity.class);
                startActivity(intent);
            }
        });

        TextView imagescan = findViewById(R.id.imagescan);
        imagescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ImageActivity.class);
                intent.putExtra("source", "dashboard");
                startActivity(intent);
            }
        });

        nested_scroll_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, GalleryActivity.class);
                startActivity(intent);

            }
        });

        TextView movieDb = findViewById(R.id.movieDb);
        movieDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MovieDBActivity.class);
                startActivity(intent);

            }
        });
        TextView create_pdf = findViewById(R.id.create_pdf);
        create_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        TextView tv_fingerprint = findViewById(R.id.tv_fingerprint);
        tv_fingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Fingerprint_Activity.class);

                startActivity(intent);

            }
        });

        TextView tv_twitter = findViewById(R.id.tv_twitter);
        tv_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, TwitterAPIActivity.class);

                startActivity(intent);

            }
        });
        TextView tv_gContacts = findViewById(R.id.tv_gContacts);
        tv_gContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        TextView test = findViewById(R.id.tv_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //shortToast("No Activity Defined");

            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        checkAndRequestPermissions();
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        // [START_EXCLUDE]
                        FirebaseAuth.getInstance().signOut();
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Dashboard.this);
        alertDialog.setTitle("Exit Application");
        alertDialog.setMessage("Do you want to exist the application ?");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user inout is positive close the dialog box and close the application
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Intent intent1 = new Intent(Dashboard.this, FusedLocationService.class);
                stopService(intent1);
                finish();
                System.exit(0);

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // if user input is negative close the dialog and do nothing
                dialog.dismiss();
            }
        });
        alertDialog.show();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

