package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.Fragments.MasterFragments.UserMasterFragment;
import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestUserDashboard extends Fragment {

    TextView events_participate,events_calender;
    String user_id;
    public FestUserDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_facebook for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_user_dashboard, container, false);

        events_calender = (TextView) root.findViewById(R.id.events_calender);
        events_participate = (TextView) root.findViewById(R.id.event_participate);
        user_id = getArguments().getString("user_id");
        events_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        events_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("user_id", user_id);
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new EventParticipateFragment();
                fragment.setArguments(arg);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

}
