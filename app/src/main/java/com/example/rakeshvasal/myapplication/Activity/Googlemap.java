package com.example.rakeshvasal.myapplication.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by User on 9/18/2016.
 */
public class Googlemap extends ActionBarActivity implements LocationListener, OnMapReadyCallback {
    private GoogleMap googlemap;
    String userlat, userlng, res_name;
    LatLng userloc;

    boolean onPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // calling init method to for initialization
        Intent i = getIntent();
        if (i != null) {
            userlat = i.getStringExtra("lat");
            userlng = i.getStringExtra("long");
            res_name = i.getStringExtra("res_name");
            if (Utils.is_Connected_To_Internet(Googlemap.this)) {
                init();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

            }


        }
    }

    private void init() {
        if (Utils.isConnectedToGps(Googlemap.this)) {

            //initializing google maps & throwing error if failed to do so.
            if (googlemap == null) {
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
        } else {

       Utils.showSettingsAlert(Googlemap.this);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (onPause) {

            init();
            onPause = false;
        } else {
            onPause = false;
        }

    }

    protected void onPause() {
        super.onPause();
        onPause = true;
    }

    public void getuserlocation() {
        //if the user location is not available try to obtain it once we get it update it using main ui thread
        if (!userlat.equalsIgnoreCase("0.0") && !userlng.equalsIgnoreCase("0.0")) {
            userloc = new LatLng(Double.parseDouble(userlat), Double.parseDouble(userlng));
            googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(userloc, 12));
            googlemap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(userlat), Double.parseDouble(userlng)))
                    .title(res_name));
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googlemap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        getuserlocation();

        if (googleMap == null) {
            Toast.makeText(getApplicationContext(), "Couldn't initialize google maps ! try again.", Toast.LENGTH_LONG).show();
        }
    }
}