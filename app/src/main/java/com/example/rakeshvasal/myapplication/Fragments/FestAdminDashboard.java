package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.Custom_Adapters.CardAdapter;
import com.example.rakeshvasal.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestAdminDashboard extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FestAdminDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_facebook for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_admin_dashboard, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the activity_facebook size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear activity_facebook manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        initalizeList();
        //CardAdapter adapter = new CardAdapter();
        //mRecyclerView.setAdapter(adapter);
        return root;
    }

    private void initalizeList(){
        String[] ElementNames = new String[]{"Events","Users","Locations","Commitee Members"};
        int[] covers = new int[]{
                R.drawable.events,
                R.drawable.locations,
                R.drawable.people,
                R.drawable.commitee};
        String[] SubTitles = new String[]{"1","2","3","4"};

    }

}
