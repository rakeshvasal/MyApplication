package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestUserDashboard extends Fragment {


    public FestUserDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_facebook for this fragment
        return inflater.inflate(R.layout.fragment_fest_user_dashboard, container, false);
    }

}
