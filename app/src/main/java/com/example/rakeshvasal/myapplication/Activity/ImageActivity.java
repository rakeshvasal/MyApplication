package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rakeshvasal.myapplication.Fragments.ImageAnalysisFragment;
import com.example.rakeshvasal.myapplication.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();
    }

    private void init() {


        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new ImageAnalysisFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}