package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.rakeshvasal.myapplication.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout

        if(findViewById(R.id.fragment_container)!=null){

            // if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.

            if (savedInstanceState != null) {
                return;
            }

            HomeFragment homeFragment = new HomeFragment();
            //getFragmentManager().beginTransaction().add(R.id.fragment_container,homeFragment).commit();

        }
    }
}
