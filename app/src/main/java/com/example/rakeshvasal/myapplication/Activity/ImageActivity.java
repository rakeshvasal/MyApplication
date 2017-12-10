package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rakeshvasal.myapplication.Fragments.ImageAnalysisFragment;
import com.example.rakeshvasal.myapplication.Fragments.OpenSourceCodeFragment;
import com.example.rakeshvasal.myapplication.Fragments.ViewImageFragment;
import com.example.rakeshvasal.myapplication.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                try {
                    //Utils.openSourceFile(Device_Info.this, "Device_Info", "java");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                arg.putString("image_path", "https://firebasestorage.googleapis.com/v0/b/myapplication-8f68b.appspot.com/o/mountains.jpg?alt=media&token=e61f3298-b821-4dab-ae7e-81f65021fdcd");
                Fragment fragment = new ImageAnalysisFragment();
                transaction.add(R.id.fragment_container, fragment);
                fragment.setArguments(arg);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }
}