package com.example.rakeshvasal.myapplication.Activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.DatabaseHelper.DatabaseHelper;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.example.rakeshvasal.myapplication.R;


import java.io.IOException;
import java.util.List;

public class Search_Place_Activity extends BaseActivity implements OnMapReadyCallback {

    LinearLayout main;
    EditText et_place_name;
    Button search_button;
    GoogleMap map;
    ImageView location, directions, add_location, show_list;
    Address address;
    boolean isplaceavailable = false;
    public static int SHOWLIST = 1;
    SharedPreferences preferences;
    Double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main = (LinearLayout) findViewById(R.id.main);
        search_button = (Button) findViewById(R.id.search_button);
        et_place_name = (EditText) findViewById(R.id.et_place_name);
        preferences = getSharedPreferences("userLocation", Context.MODE_PRIVATE);
        latitude = Double.parseDouble(preferences.getString("userlat","0.0"));
        longitude = Double.parseDouble(preferences.getString("userlng","0.0"));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(main.getWindowToken(), 0);
        }
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.is_Connected_To_Internet(Search_Place_Activity.this)) {

                    String place_name = et_place_name.getText().toString();
                    if (!place_name.equalsIgnoreCase("")) {
                        findLocation(place_name);
                    } else {
                        //Toast.makeText(Search_Place_Activity.this, "Enter Place", Toast.LENGTH_SHORT).show();
                        shortToast("Enter Place");
                    }
                } else {
                    //Toast.makeText(Search_Place_Activity.this, getResources().getString(R.string.Check_Internet), Toast.LENGTH_SHORT).show();
                    shortToast(getResources().getString(R.string.Check_Internet));
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
                //UserLocation userLoc = new UserLocation(Search_Place_Activity.this);

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
                //map.setMyLocationEnabled(true);

                LatLng latLng = new LatLng(latitude, longitude);
                map.addMarker(new MarkerOptions().position(latLng).title("You are here"));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        });
        directions = (ImageView) findViewById(R.id.get_directions);
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address != null) {
                    if (isplaceavailable) {

                    }
                }
            }
        });
        show_list = (ImageView) findViewById(R.id.show_search_items);
        show_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String[] name;
                ArrayAdapter adapter;
                DatabaseHelper helper = DatabaseHelper.getInstance(Search_Place_Activity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                String[] column = new String[]{DatabaseHelper.LOCATION_NAME};
                Cursor cursor = db.query(DatabaseHelper.LOCATION_TABLE, column, null, null, null, null, null, null);
                name = new String[cursor.getCount()];
                int i = 0;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        name[i] = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME));
                    }
                }
                adapter = new ArrayAdapter(Search_Place_Activity.this,R.activity_facebook.support_simple_spinner_dropdown_item,name);*/
                Intent intent = new Intent(Search_Place_Activity.this, ShowLocationList.class);
                //  startActivityForResult(intent,SHOWLIST);
                startActivity(intent);
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
                if (address != null && !address.equals("")) {
                    isplaceavailable = true;
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    Double latitude = address.getLatitude();
                    Double longitude = address.getLongitude();
                    map.addMarker(new MarkerOptions().position(latLng).title(place_name));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                } else {
                    isplaceavailable = false;
                }

            } else {
                isplaceavailable = false;
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

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
        LatLng latLng = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions().position(latLng).title("You are here"));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

    }

    private void addtolist(Address address) {

        if (address != null) {
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            Double latitude = address.getLatitude();
            Double longitude = address.getLongitude();
            String countryName = address.getCountryName();
            String placeName = address.getFeatureName();

            DatabaseHelper helper = DatabaseHelper.getInstance(Search_Place_Activity.this);
            SQLiteDatabase db = helper.getWritableDatabase();

            String[] column = new String[]{DatabaseHelper.LOCATION_NAME};
            Cursor cursor = db.query(DatabaseHelper.LOCATION_TABLE, column, DatabaseHelper.LOCATION_NAME + " ='" + placeName + "'", null, null, null, null, null);
            if (cursor.getCount() <= 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.LOCATION_NAME, placeName);
                contentValues.put(DatabaseHelper.LOCATION_LATLNG, "" + latLng);
                contentValues.put(DatabaseHelper.LOCATION_LAT, latitude);
                contentValues.put(DatabaseHelper.LOCATION_LONG, longitude);
                contentValues.put(DatabaseHelper.LOCATION_COUNTRY_NAME, countryName);

                boolean insert = db.insert(DatabaseHelper.LOCATION_TABLE, null, contentValues) > 0;
                if (insert) {
                    db.close();
                    Toast.makeText(Search_Place_Activity.this, "Location Insert Success", Toast.LENGTH_SHORT).show();
                } else {
                    db.close();
                    Toast.makeText(Search_Place_Activity.this, "Location Insert Fail", Toast.LENGTH_SHORT).show();
                }
            }
        }
        // Add the values into the location table and create a list view using dialog box
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Search_Place_Activity.this, Dashboard.class);
        startActivity(intent);
    }
}
