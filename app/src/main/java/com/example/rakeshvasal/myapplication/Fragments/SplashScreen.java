package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Fragments.SearchPage;
import com.example.rakeshvasal.myapplication.R;


/**
 * Created by User on 9/17/2016.
 */
public class SplashScreen extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_splash_screen, container, false);
        init();
        return root;
    }

    private void init(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Fragment fragment = new SearchPage();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        },1000);
    }
}
