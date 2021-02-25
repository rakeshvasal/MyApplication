package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventCalenderFragment extends Fragment {


    public EventCalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_calender, container, false);
    }

}
