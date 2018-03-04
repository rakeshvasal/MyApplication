package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Fragments.TwitterSearchFragment;
import com.example.rakeshvasal.myapplication.R;

/**
 * Created by Rakeshvasal on 04-Mar-18.
 */

public class TwitterAPIActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zomato);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new TwitterSearchFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}
