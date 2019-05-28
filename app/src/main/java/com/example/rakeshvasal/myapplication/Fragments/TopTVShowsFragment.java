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
public class TopTVShowsFragment extends BaseFragment {


    public TopTVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_top_tvshows, container, false);

        return root;
    }

}
