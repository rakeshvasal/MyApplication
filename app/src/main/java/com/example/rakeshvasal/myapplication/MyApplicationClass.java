package com.example.rakeshvasal.myapplication;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

/**
 * Created by Rakeshvasal on 15-Nov-17.
 */

public class MyApplicationClass extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

    }
}
