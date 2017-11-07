package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;


public class EventSelectFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_select, container, false);
    }


}
