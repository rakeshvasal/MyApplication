package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rakeshvasal.myapplication.Custom_Adapters.CardAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

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

                }
                if (position==2){

                }
                if (position==3){

                }
            }
        });

    }


}
