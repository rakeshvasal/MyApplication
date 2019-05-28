package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CricAPIHome extends BaseFragment {


    public CricAPIHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_cric_apihome, container, false);


        TextView New_Matches = (TextView) view.findViewById(R.id.new_matches);
        New_Matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CricketMatchesFragment cricfrag = new CricketMatchesFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, cricfrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TextView Calender = (TextView) view.findViewById(R.id.matches_calender);
        Calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MatchCalenderFragment cricfrag = new MatchCalenderFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, cricfrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TextView PlayerFinder = (TextView) view.findViewById(R.id.player_finder);
        PlayerFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerFinderFragment cricfrag = new PlayerFinderFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.fragment_container, cricfrag);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TextView Old_Matches = (TextView) view.findViewById(R.id.old_matches);
        Old_Matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

}
