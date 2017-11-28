package com.example.rakeshvasal.myapplication.Fragments.MasterFragments;


import android.app.Fragment;
import android.app.FragmentManager;
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

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateEventFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.ChildEventListener;
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
public class EventsMasterFragment extends BaseFragment {

    Button add_event,search_event;
    EditText search_text;
    RecyclerView recyclerView;
    private DatabaseReference mEventsDatabase;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference ref;
    FragmentManager fm;
    public EventsMasterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_events, container, false);

        add_event = (Button) rootview.findViewById(R.id.addevent);
        search_event = (Button) rootview.findViewById(R.id.searchevent);
        search_text = (EditText) rootview.findViewById(R.id.searchtext);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mEventsDatabase = mFirebaseInstance.getReference();
        fm = getFragmentManager();
        ref = mEventsDatabase.child("events");
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        fetchallevents();
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                arg.putString(Utils.TASK, Utils.ADD_TASK);
                Fragment fragment = new AddUpdateEventFragment();
                fragment.setArguments(arg);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        search_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                if (search_text.getText().toString().equalsIgnoreCase("")) {
                    fetchallevents();
                } else {
                    readData("eventName",search_text.getText().toString());
                }
            }
        });

        return rootview;
    }

    private void fetchallevents() {


        final List<Events> mEventsEntries = new ArrayList<>();
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Events events = eventsnapshot.getValue(Events.class);
                        mEventsEntries.add(events);

                    }
                    EventsMasterAdapter adapter = new EventsMasterAdapter(getActivity(),mEventsEntries,fm);
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

    private void readData(String parameter,String searchtext) {

        final List<Events> mEventsEntries = new ArrayList<>();
        ref.orderByChild(parameter).equalTo(searchtext).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Events events = dataSnapshot.getValue(Events.class);
                mEventsEntries.add(events);
                closeProgressDialog();
                EventsMasterAdapter adapter = new EventsMasterAdapter(getActivity(), mEventsEntries,fm);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                closeProgressDialog();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                closeProgressDialog();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                closeProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                closeProgressDialog();
            }
        });

    }

}
