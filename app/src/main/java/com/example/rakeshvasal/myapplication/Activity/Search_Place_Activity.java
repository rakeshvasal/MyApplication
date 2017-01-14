package com.example.rakeshvasal.myapplication.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.Services.UserLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

import com.example.rakeshvasal.myapplication.R;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Search_Place_Activity extends AppCompatActivity implements OnMapReadyCallback {

    EditText et_place_name;
    Button search_button;
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        search_button = (Button) findViewById(R.id.search_button);
        et_place_name = (EditText) findViewById(R.id.et_place_name);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String place_name = et_place_name.getText().toString();
                findLocation(place_name);
            }
        });
    }

    private void findLocation(String place_name) {

        List<Address> addressList = null;

        if (place_name != null || !place_name.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(place_name, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Double latitude = address.getLatitude();
            Double longitude = address.getLongitude();
            map.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        UserLocation userLoc = new UserLocation(Search_Place_Activity.this);
        LatLng latLng = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng).title("You are here"));
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
        map.setMyLocationEnabled(true);
    }

}
