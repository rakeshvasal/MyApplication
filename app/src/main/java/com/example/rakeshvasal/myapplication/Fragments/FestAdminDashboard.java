package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.Fragments.MasterFragments.EventsMasterFragment;
import com.example.rakeshvasal.myapplication.Fragments.MasterFragments.LocationMasterFragment;
import com.example.rakeshvasal.myapplication.Fragments.MasterFragments.UserMasterFragment;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestAdminDashboard extends Fragment{

    ListView mListView;
    DatabaseReference mDatabase;

    public FestAdminDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_facebook for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_admin_dashboard, container, false);
        mListView = (ListView) root.findViewById(R.id.my_recycler_view);



        initalizeList();
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        return root;
    }

    private void initalizeList() {
        String[] ElementNames = new String[]{"Events", "Users", "Locations", "Commitee Members"};

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.admindashboardlist, R.id.item, ElementNames);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    Fragment fragment = new EventsMasterFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (position==1){
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    Fragment fragment = new UserMasterFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }
                if (position==2){
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    Fragment fragment = new LocationMasterFragment();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                if (position==3){

                }
            }
        });

    }


}
