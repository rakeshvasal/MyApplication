package com.example.rakeshvasal.myapplication.Activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Fragments.SplashScreen;
import com.example.rakeshvasal.myapplication.R;

public class ZomatoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new SplashScreen();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }
}
