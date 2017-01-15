package com.example.rakeshvasal.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.webkit.HttpAuthHandler;

import com.example.rakeshvasal.myapplication.R;


/**
 * Created by User on 9/17/2016.
 */
public class SplashScreen extends ActionBarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this,SearchPage.class);
                startActivity(i);
                finish();
            }
        },1000);
    }
}
