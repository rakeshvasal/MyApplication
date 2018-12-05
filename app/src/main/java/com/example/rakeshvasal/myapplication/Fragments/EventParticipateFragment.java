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

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.EventSelectAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.EventUserMap;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.GetterSetter.Transactions;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EventParticipateFragment extends BaseFragment {

    String user_id, trans_id;
    Button submit;
    RecyclerView recyclerView;
    private DatabaseReference mUserDatabase, userref, eventsref, eventuserref, amountsref;
    FirebaseDatabase mFirebaseInstance;
    String[] events, eventsamounts;
    int amt;

    public EventParticipateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_event_participate, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        submit = (Button) root.findViewById(R.id.submit);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        user_id = getArguments().getString("user_id");

        try {

            mFirebaseInstance = FirebaseDatabase.getInstance();
            mUserDatabase = mFirebaseInstance.getReference();
            userref = mUserDatabase.child("users");
            eventsref = mUserDatabase.child("events");
            eventuserref = mUserDatabase.child("events_users");
            amountsref = mUserDatabase.child("amount_transactions");

        } catch (Exception e) {
            e.printStackTrace();
        }
        fetchDetails(user_id);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitdata();
            }
        });

        return root;
    }

    private void fetchDetails(String user_id) {

        showProgressDialog();
        final List<Events> mEventEntries = new ArrayList<>();
        try {
            eventsref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {

                        Events events = eventsnapshot.getValue(Events.class);
                        mEventEntries.add(events);

                    }
                    closeProgressDialog();
                    EventSelectAdapter adapter = new EventSelectAdapter(getActivity(), mEventEntries);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

    private void submitdata() {
        showProgressDialog();
        //ArrayList<Integer> arrayList = Utils.getIntegerArray(Utils.eventselected);
        //ArrayList<String> arrayList1 = Utils.getStringArray(arrayList);
        //-----------------------------------
        //ArrayList<Integer> arrayList2 = Utils.getIntegerArray(Utils.eventselectedamounts);
        //ArrayList<String> arrayList3 = Utils.getStringArray(arrayList2);
        //-----------------------------------
        Set<String> hs = new HashSet<>();
        //hs.addAll(arrayList1);
        //arrayList1.clear();
        //arrayList1.addAll(hs);
        //-----------------------------------
        Set<String> hs1 = new HashSet<>();
        //hs1.addAll(arrayList3);
        //arrayList3.clear();
        //arrayList3.addAll(hs1);
        //-----------------------------------
        //arrayList = Utils.getIntegerArray(arrayList1);
        //-----------------------------------
        //arrayList2 = Utils.getIntegerArray(arrayList3);
        //-----------------------------------
        //Collections.sort(arrayList, new CompareArrayList());
        //Collections.sort(arrayList2, new CompareArrayList());
        //-----------------------------------
        //arrayList1.clear();
        //-----------------------------------
        //arrayList3.clear();
        //-----------------------------------
        hs.clear();
        hs.addAll(Utils.eventselected);
        ArrayList<String> chkList = new ArrayList<String>();
        chkList.addAll(Utils.eventselected);
        events = new String[chkList.size()];
        //-----------------------------------
        hs1.clear();
        hs1.addAll(Utils.eventselectedamounts);
        ArrayList<String> chkList1 = new ArrayList<String>();
        chkList1.addAll(Utils.eventselectedamounts);
        eventsamounts = new String[chkList1.size()];
        //-----------------------------------

        for (int i = 0; i < chkList.size(); i++) {

            Log.d("eventsname", chkList.get(i));
            Log.d("eventsamount", chkList1.get(i));
            events[i] = chkList.get(i);
            eventsamounts[i] = chkList1.get(i);

        }

        submitamounts(eventsamounts);

    }

    private void submitamounts(String[] eventsamounts) {

        int finalamount = 0;
        for (int i = 0; i < eventsamounts.length; i++) {
            int amt = Integer.parseInt(eventsamounts[i]);
            finalamount = finalamount + amt;
        }

        Transactions transactions = new Transactions(user_id, "", finalamount);
        try {
            String transactionid = amountsref.push().getKey();
            transactions.setTrans_id(transactionid);
            amountsref.child(transactionid).setValue(transactions);
            trans_id = transactionid;
            amt = finalamount;
            submitevents(trans_id, events);
        } catch (Exception e) {
            e.printStackTrace();
            shortToast("Error while inserting/updating user" + e.getMessage());
        }

    }

    private void submitevents(String trans_id, String[] events) {

        for (int i = 0; i < events.length; i++) {
            EventUserMap map = new EventUserMap(user_id, events[i], trans_id, "");
            String mapid = eventuserref.push().getKey();
            map.setMap_id(mapid);
            eventuserref.child(mapid).setValue(map);
        }

        Bundle arg = new Bundle();
        arg.putInt("amt",amt);
        arg.putString("trans_id",trans_id);
        arg.putStringArray("eventarray",events);
        FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
        Fragment fragment = new SuccessFragment();
        fragment.setArguments(arg);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        closeProgressDialog();
    }

}
