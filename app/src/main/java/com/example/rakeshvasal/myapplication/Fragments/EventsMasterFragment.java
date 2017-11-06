package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.Activity.GalleryActivity;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsMasterFragment extends Fragment {

    Button add_event,search_event;
    EditText search_text;
    RecyclerView recyclerView;
    private DatabaseReference mEventsDatabase;
    FirebaseDatabase mFirebaseInstance;
    public EventsMasterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_events, container, false);

        add_event = (Button) rootview.findViewById(R.id.addevent);
        search_event = (Button) rootview.findViewById(R.id.searchevent);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mEventsDatabase = mFirebaseInstance.getReference();
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        fetchallevents();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GalleryActivity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


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

    private void fetchallevents() {
        DatabaseReference ref = mEventsDatabase.child("events");

        final List<Events> mEventsEntries = new ArrayList<>();
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Events events = eventsnapshot.getValue(Events.class);
                        mEventsEntries.add(events);

                    }
                    EventsMasterAdapter adapter = new EventsMasterAdapter(getActivity(),mEventsEntries);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("eventsdatabaseerror", databaseError.getMessage());
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
