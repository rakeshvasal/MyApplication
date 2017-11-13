package com.example.rakeshvasal.myapplication.Utilities;

/**
 * Created by ${Azahar} on 8/2/2016.
 */
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class CheckConnectivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {

        boolean isConnected = isConnectedToGps(context);
        if(isConnected){
           // Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_LONG).show();
        }
        else{
           // Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isConnectedToGps(Context activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}