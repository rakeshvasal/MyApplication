package com.example.rakeshvasal.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

/**
 * Created by Axisvation on 10/28/2017.
 */

public class MatchDetailsFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        // Inflate the activity_facebook for this fragment
        view = inflater.inflate(R.layout.fragment_match_details, container, false);



        return view;
    }
}
