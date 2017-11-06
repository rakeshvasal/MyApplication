package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEventFragment extends Fragment {

    EditText name, location, contact, entryfees;
    Button add;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;

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

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String strname = name.getText().toString();
                    String strlocation = location.getText().toString();
                    String strcontact = contact.getText().toString();
                    String strentryfees = entryfees.getText().toString();

                    addUpdateEvent(strname, strlocation, strcontact, strentryfees);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootview;
    }

    private void addUpdateEvent(String strname, String strlocation, String strcontact, String strentryfees) {
        try {
            Events events = new Events(strname, strlocation, strcontact, strentryfees);

            String userId = mDatabase.push().getKey();

            mDatabase.child(userId).setValue(events);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
