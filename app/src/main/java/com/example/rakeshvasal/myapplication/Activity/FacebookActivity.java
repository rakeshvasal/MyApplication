package com.example.rakeshvasal.myapplication.Activity;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Fragments.FacebookFragment;
import com.example.rakeshvasal.myapplication.R;
import com.facebook.FacebookSdk;

/**
 * Created by Rakeshvasal on 02-Apr-17.
 */

public class FacebookActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = new FacebookFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
       /* if (fragment == null) {
            fragment = new FacebookFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }*/
    }



}

