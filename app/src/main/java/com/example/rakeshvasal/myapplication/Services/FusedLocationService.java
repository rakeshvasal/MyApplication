package com.example.rakeshvasal.myapplication.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class FusedLocationService extends Service implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    SharedPreferences preferences, userpref;
    GoogleApiClient googleApiClient;
    public Location location = null;

    LocationManager mlocManager;
    public Double lattitude;
    public Double longitude;
    boolean canGetLocation = false;
    Context mContext;
    private static final String TAG = "LocationService";
    private static final long INTERVAL = 1000 * 60 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 60 * 10;

    IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
        //throw new UnsupportedOperationException("Not yet implemented");

    }

    public class LocalBinder extends Binder {
        public FusedLocationService getServerInstance() {
            return FusedLocationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public FusedLocationService() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        preferences = getSharedPreferences("userLocation", Context.MODE_PRIVATE);
        getlocation();
    }

    public Location getcurrentLocation() {

        return location;
    }

    private void getlocation() {


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();


        if (location != null) {
            canGetLocation = true;
            String lat = String.valueOf(location.getLatitude());
            String lng = String.valueOf(location.getLongitude());
            Log.d(TAG, "Latitude is " + lat);
            Log.d(TAG, "Longitude is " + lng);
            Log.d(TAG, "Accuracy is " + location.getAccuracy());
            Log.d(TAG, "Provider is " + location.getProvider());
            preferences.edit().putString("userlat", "" + location.getLatitude()).apply();
            preferences.edit().putString("userlng", "" + location.getLongitude()).apply();
        }
        //  locationManager= (LocationManager) context.getSystemService(LOCATION_SERVICE);

    }


    //connection change listener for fused api
    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(getApplicationContext(),"Fused api connected",Toast.LENGTH_SHORT).show();
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);


    }

    @Override
    public void onConnectionSuspended(int i) {
        canGetLocation = false;
    }

    // on connection failed listener for the fused api
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        canGetLocation = false;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    //location listener override methods
    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getlocation();
    }

    public double getLatitude() {
        if (location != null) {
            lattitude = location.getLatitude();
        }
        // return latitude
        return lattitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }


}
