package com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments;


import android.app.Fragment;
import android.os.Bundle;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class AddUpdateEventFragment extends Fragment {

    EditText name, location, contact, entryfees;
    Button add;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    int size;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_add_event, container, false);

        try {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            mDatabase = mFirebaseInstance.getReference("events");

            name = (EditText) rootview.findViewById(R.id.eventname);
            location = (EditText) rootview.findViewById(R.id.eventlocation);
            contact = (EditText) rootview.findViewById(R.id.entry_fees);
            entryfees = (EditText) rootview.findViewById(R.id.contact_person);
            add = (Button) rootview.findViewById(R.id.add);
            fetchallevents();
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String strname = name.getText().toString();
                    String strlocation = location.getText().toString();
                    String strcontact = contact.getText().toString();
                    String strentryfees = entryfees.getText().toString();
                    int id = (int)System.currentTimeMillis();
                    if (id<0){
                        id = id * - 1;
                    }
                    addUpdateEvent(strname, strlocation, strcontact, strentryfees, id);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootview;
    }

    private void addUpdateEvent(String strname, String strlocation, String strcontact, String strentryfees, int id) {
        try {
            Events events = new Events(strname, strlocation, strcontact, strentryfees, id);

            String userId = mDatabase.push().getKey();

            mDatabase.child(userId).setValue(events);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchallevents() {


        final List<Events> mEventsEntries = new ArrayList<>();
        try {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Events events = eventsnapshot.getValue(Events.class);
                        mEventsEntries.add(events);
                        size = mEventsEntries.size();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("eventsdatabaseerror", databaseError.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
