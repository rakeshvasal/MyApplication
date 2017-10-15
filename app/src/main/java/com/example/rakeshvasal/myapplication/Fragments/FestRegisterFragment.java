package com.example.rakeshvasal.myapplication.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

public class FestRegisterFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        // Inflate the activity_facebook for this fragment
        view = inflater.inflate(R.layout.fragment_fest_register, container, false);

        operations();

        return view;


    }

    private void operations(){


    }

    public FestRegisterFragment() {
        // Required empty public constructor
    }










}
