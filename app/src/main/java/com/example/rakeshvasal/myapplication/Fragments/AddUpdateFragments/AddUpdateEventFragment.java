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
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.DatePickerClass;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddUpdateEventFragment extends BaseFragment {

    EditText name, location, contact, entryfees,contactno;
    TextView eventfrm, eventto;
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
            contact = (EditText) rootview.findViewById(R.id.contact_person);
            entryfees = (EditText) rootview.findViewById(R.id.entry_fees);
            eventfrm = (TextView) rootview.findViewById(R.id.event_start_date);
            eventto = (TextView) rootview.findViewById(R.id.event_end_date);
            contactno = (EditText) rootview.findViewById(R.id.contact_number);
            add = (Button) rootview.findViewById(R.id.add);

            fetchallevents();

            clickListeners();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootview;
    }

    private void clickListeners() {

        //final DatePickerClass datePickerClass = new DatePickerClass();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkname = checkemptyedittext(name);
                boolean checkcontact = checkemptyedittext(contact);
                boolean checkentryfees = checkemptyedittext(entryfees);
                boolean checkdate = validateDate();

                if (!checkdate){
                    shortToast("Irregular Dates");
                    return;
                }else if (checkname) {
                    shortToast("Enter Name");
                    return;
                } else if (checkcontact) {
                    shortToast("Enter Contact");
                    return;
                } else if (checkentryfees) {
                    shortToast("Enter Fees");
                    return;
                }

                String strname = name.getText().toString();
                String strlocation = location.getText().toString();
                String strcontact = contact.getText().toString();
                String strentryfees = entryfees.getText().toString();
                String eventstartdate = eventfrm.getText().toString();
                String eventenddate = eventto.getText().toString();
                String contactnumber = contactno.getText().toString();

                int id = (int) System.currentTimeMillis();
                if (id < 0) {
                    id = id * -1;
                }
                addUpdateEvent(strname, strlocation, strcontact, strentryfees,contactnumber, id,eventstartdate,eventenddate);

            }
        });

        eventfrm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerClass.getSetDate(getActivity(), eventfrm, "Event Start Date");
            }
        });

        eventto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerClass.getSetDate(getActivity(), eventto, "Event End Date");
            }
        });
    }

    private boolean validateDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date1 = formatter.parse(eventfrm.getText().toString());
            Date date2 = formatter.parse(eventto.getText().toString());

            if (date1.compareTo(date2)<0)
            {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addUpdateEvent(String strname, String strlocation, String strcontact, String strentryfees,String contactno, int id,String eventstartdate,String eventenddate) {
        try {
            Events events = new Events(strname, strlocation, strcontact, strentryfees,contactno,id,eventstartdate,eventenddate);

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
