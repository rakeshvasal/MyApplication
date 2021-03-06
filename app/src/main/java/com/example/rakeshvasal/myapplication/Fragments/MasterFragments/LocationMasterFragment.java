package com.example.rakeshvasal.myapplication.Fragments.MasterFragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.Custom_Adapters.LocationMasterAdapter;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateLocationFragment;
import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.Interface.CentralCallbacks;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.ServiceCalls.CentralApiCenter;
import com.example.rakeshvasal.myapplication.UIError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class LocationMasterFragment extends BaseFragment {

    Button add, search;
    EditText search_text;
    RecyclerView recyclerView;
    String[] name;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference ref;
    int i = 0;

    public LocationMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_location_master, container, false);

        add = (Button) rootview.findViewById(R.id.add);
        search = (Button) rootview.findViewById(R.id.search);
        search_text = (EditText) rootview.findViewById(R.id.searchtext);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        ref = mFirebaseInstance.getReference("locations");

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                android.app.Fragment fragment = new AddUpdateLocationFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        fetchallLocations();


        return rootview;
    }

    private void fetchallLocations() {
        showProgressDialog();

        try {

            CentralApiCenter.getInstance().getAllLocationsList(new CentralCallbacks() {
                @Override
                public void onSuccess(Object response) {
                    List<String> locationsList = (List<String>) response;
                    LocationMasterAdapter adapter = new LocationMasterAdapter(getActivity(), locationsList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(UIError error) {
                    closeProgressDialog();
                }
            });


            //////
           /* ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int size = (int) dataSnapshot.getChildrenCount();
                    name = new String[size];
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {


                        name[i] = (String) eventsnapshot.child("name").getValue();
                        Log.d("results", eventsnapshot.toString());
                        i++;


                    }
                    i = 0;
                    closeProgressDialog();
                    LocationMasterAdapter adapter = new LocationMasterAdapter(getActivity(), name);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    closeProgressDialog();
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                }
            });*/


        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }
    }

}
