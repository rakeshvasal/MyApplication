package com.example.rakeshvasal.myapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Services.UserLocation;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
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

    LinearLayout main;
    EditText et_place_name;
    Button search_button;
    GoogleMap map;
    ImageView location,directions,add_location,show_list;
    Address address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        main = (LinearLayout) findViewById(R.id.main);
        search_button = (Button) findViewById(R.id.search_button);
        et_place_name = (EditText) findViewById(R.id.et_place_name);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(main.getWindowToken(), 0);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.is_Connected_To_Internet(Search_Place_Activity.this)) {
                    String place_name = et_place_name.getText().toString();
                    findLocation(place_name);
                } else {
                    Toast.makeText(Search_Place_Activity.this, getResources().getString(R.string.Check_Internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        add_location = (ImageView) findViewById(R.id.add_to_list);
        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtolist(address);
            }
        });
        location = (ImageView) findViewById(R.id.my_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocation userLoc = new UserLocation(Search_Place_Activity.this);
                LatLng latLng = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                if (ActivityCompat.checkSelfPermission(Search_Place_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Search_Place_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        });
        directions = (ImageView) findViewById(R.id.get_directions);
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address!=null){
                    //getDirections();
                }
            }
        });
        show_list = (ImageView) findViewById(R.id.show_search_items);
        show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add Cursor from location table and add it to base adapter
            }
        });
    }

    private void findLocation(String place_name) {

        List<Address> addressList = null;

        if (place_name != null || !place_name.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(place_name, 5);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null) {

                Address address = addressList.get(0);
                this.address = address;
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                Double latitude = address.getLatitude();
                Double longitude = address.getLongitude();
                map.addMarker(new MarkerOptions().position(latLng).title(place_name));
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        UserLocation userLoc = new UserLocation(Search_Place_Activity.this);
        LatLng latLng = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng).title("You are here"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
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

    private void addtolist(Address address){

        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        Double latitude = address.getLatitude();
        Double longitude = address.getLongitude();
        String countryName = address.getCountryName();
        String placeName = address.getFeatureName();

        // Add the values into the location table and create alist view using dialog box
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
