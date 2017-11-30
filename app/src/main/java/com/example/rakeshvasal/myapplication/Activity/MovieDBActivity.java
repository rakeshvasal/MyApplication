package com.example.rakeshvasal.myapplication.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rakeshvasal.myapplication.Fragments.MovieDb_HomeFragment;
import com.example.rakeshvasal.myapplication.Fragments.SplashScreen;
import com.example.rakeshvasal.myapplication.R;

public class MovieDBActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new MovieDb_HomeFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
