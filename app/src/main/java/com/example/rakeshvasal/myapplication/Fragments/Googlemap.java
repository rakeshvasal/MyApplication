package com.example.rakeshvasal.myapplication.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.BaseFragment;
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
public class Googlemap extends Fragment implements LocationListener, OnMapReadyCallback {
    private GoogleMap googlemap;
    String userlat, userlng, res_name;
    LatLng userloc;

    boolean onPause = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_google_map, container, false);

        userlat = getArguments().getString("lat");
        userlng = getArguments().getString("long");
        res_name = getArguments().getString("res_name");

        if (Utils.is_Connected_To_Internet(getActivity())) {
            init();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

        }
        return root;
    }

    private void init() {
        if (Utils.isConnectedToGps(getActivity())) {

            //initializing google maps & throwing error if failed to do so.
            if (googlemap == null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    MapFragment mapFragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
                /*MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                        .findFragmentById(R.id.map);*/

            }
        } else {

       Utils.showSettingsAlert(getActivity());


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (onPause) {
            init();
            onPause = false;
        } else {
            onPause = false;
        }

    }

    public void onPause() {
        super.onPause();
        onPause = true;
    }

    public void getuserlocation() {
        //if the user location is not available try to obtain it once we get it update it using main ui thread
        if (!userlat.equalsIgnoreCase("0.0") && !userlng.equalsIgnoreCase("0.0")) {
            userloc = new LatLng(Double.parseDouble(userlat), Double.parseDouble(userlng));
            googlemap.addMarker(new MarkerOptions().position(userloc).title(res_name));
            googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(userloc, 12));
            /*googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(userloc, 12));
            googlemap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(userlat), Double.parseDouble(userlng)))
                    .title(res_name));*/
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googlemap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //googleMap.setMyLocationEnabled(true);

        getuserlocation();

        if (googleMap == null) {
            Toast.makeText(getActivity(), "Couldn't initialize google maps ! try again.", Toast.LENGTH_LONG).show();
        }
    }
}