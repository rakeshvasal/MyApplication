package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookHomeDashboard extends Fragment {


    public FacebookHomeDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_facebook_home_dashboard, container, false);
    }

}
