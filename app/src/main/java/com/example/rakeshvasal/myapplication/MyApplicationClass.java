package com.example.rakeshvasal.myapplication;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.Gravity;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.Activity.Dashboard;
import com.google.firebase.FirebaseApp;


/**
 * Created by Rakeshvasal on 15-Nov-17.
 */

public class MyApplicationClass extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
