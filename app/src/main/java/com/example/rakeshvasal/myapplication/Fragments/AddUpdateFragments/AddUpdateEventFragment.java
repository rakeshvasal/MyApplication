package com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments;


import android.app.Fragment;
import android.os.Bundle;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventsMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.GetterSetter.Locations;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.DatePickerClass;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddUpdateEventFragment extends BaseFragment {

    EditText name, location, contact, entryfees, contactno;
    TextView eventfrm, eventto;
    Button add;
    private DatabaseReference mDatabase, location_ref, comm_mem_ref,ref,childref;
    FirebaseDatabase mFirebaseInstance;
    Spinner splocation;
    String task, id;
    List<String> locid,loc_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_add_event, container, false);

        try {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            mDatabase = mFirebaseInstance.getReference("events");
            location_ref = mFirebaseInstance.getReference("locations");

            name = (EditText) rootview.findViewById(R.id.eventname);
            location = (EditText) rootview.findViewById(R.id.eventlocation);
            contact = (EditText) rootview.findViewById(R.id.contact_person);
            entryfees = (EditText) rootview.findViewById(R.id.entry_fees);
            eventfrm = (TextView) rootview.findViewById(R.id.event_start_date);
            eventto = (TextView) rootview.findViewById(R.id.event_end_date);
            contactno = (EditText) rootview.findViewById(R.id.contact_number);
            add = (Button) rootview.findViewById(R.id.add);
            splocation = (Spinner) rootview.findViewById(R.id.splocation);

            task = getArguments().getString(Utils.TASK);
            if (task.equalsIgnoreCase(Utils.UPDATE_TASK)) {
                add.setText("Update");
            } else {
                add.setText("Add");
            }
            id = getArguments().getString("userid");
            if (id == null || id.equalsIgnoreCase("")) {

            } else {
                //readData("id", id);
                FetchDetailsfromId(id);
            }
            fetchalldata();

            clickListeners();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootview;
    }

    private void clickListeners() {


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkname = checkemptyedittext(name);
                boolean checkcontact = checkemptyedittext(contact);
                boolean checkentryfees = checkemptyedittext(entryfees);
                boolean checkdate = validateDate();

                if (!checkdate) {
                    shortToast("Irregular Dates");
                    return;
                } else if (checkname) {
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


                Events events = new Events(strname, strlocation, strentryfees, strcontact, contactnumber, "", eventstartdate, eventenddate);
                int id = (int) System.currentTimeMillis();
                if (id < 0) {
                    id = id * -1;
                }
                addUpdateEvent(events);

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

            if (date1.compareTo(date2) < 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void addUpdateEvent(Events events) {
        try {
            if (task.equalsIgnoreCase(Utils.UPDATE_TASK)) {
                events.setId(id);
                mDatabase.child(id).setValue(events);
            } else if (task.equalsIgnoreCase(Utils.ADD_TASK)) {

                String id = mDatabase.push().getKey();
                events.setId(id);
                mDatabase.child(id).setValue(events);

            } else {
                shortToast("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            shortToast("Error while inserting/updating user" + e.getMessage());
        }
    }

    private void fetchalldata() {

        int i=0;
        final List<Locations> mLocationEntries = new ArrayList<>();
        try {

            location_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Locations locations = eventsnapshot.getValue(Locations.class);

                            mLocationEntries.add(locations);

                    }
                    loc_name = new ArrayList<String>(mLocationEntries.size());
                    locid = new ArrayList<String>(mLocationEntries.size());
                    for(int i =0;i<mLocationEntries.size();i++) {
                        loc_name.add(i, mLocationEntries.get(i).getLocationName());
                        locid.add(i, "" + mLocationEntries.get(i).getLocationid());
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,loc_name);
                    splocation.setAdapter(arrayAdapter);
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

    private void FetchDetailsfromId(String id) {
        showProgressDialog();
        ref = mDatabase.child(id);
        childref = ref.getRef();
        final List<Events> meventEntries = new ArrayList<>();
        try {
            childref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Events events = dataSnapshot.getValue(Events.class);
                    meventEntries.add(events);
                    String json = new Gson().toJson(meventEntries);
                    Log.d("totaljson", json);
                    closeProgressDialog();
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Log.d("jsonobject", "" + jsonObject);
                            //setValues("" + jsonObject);
                            setData(meventEntries);
                        } catch (Exception r) {
                            r.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    closeProgressDialog();
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                }
            });


        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }
    }

    private void setData(List<Events> events) {

        Events events1 = events.get(0);
        name.setText(events1.getEventName());
        location.setText(events1.getLocation());
        entryfees.setText(events1.getEntryFees());
        contact.setText(events1.getContactPerson());
        contactno.setText(events1.getContactno());
        eventfrm.setText(events1.getStartdate());
        eventto.setText(events1.getEnddate());
    }

}
