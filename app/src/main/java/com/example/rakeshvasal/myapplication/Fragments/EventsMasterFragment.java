package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsMasterFragment extends Fragment {

    Button add_event,search_event;
    EditText search_text;
    RecyclerView recyclerView;

    public EventsMasterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_events, container, false);

        add_event = (Button) rootview.findViewById(R.id.addevent);
        search_event = (Button) rootview.findViewById(R.id.searchevent);

        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new AddEventFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return rootview;
    }



}
