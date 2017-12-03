package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;


public class MovieDetailsFragment extends BaseFragment {

    String movie_id;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);

        movie_id = getArguments().getString("movieid");
        shortToast(movie_id);

        return root;
    }

}
