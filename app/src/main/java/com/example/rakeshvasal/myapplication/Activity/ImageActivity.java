package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String source = bundle.getString("source");
        String image_path = bundle.getString("image_path");
        if (source != null) {
            if (source.equalsIgnoreCase("googleurl")) {

                Bundle arg = new Bundle();
                arg.putString("image_path", image_path);
                Fragment fragment = new ViewImageFragment();
                transaction.add(R.id.fragment_container, fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            } else if (source.equalsIgnoreCase("dashboard")) {
                Bundle arg = new Bundle();
                arg.putString("image_path", image_path);
                Fragment fragment = new ImageAnalysisFragment();
                transaction.add(R.id.fragment_container, fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }
}