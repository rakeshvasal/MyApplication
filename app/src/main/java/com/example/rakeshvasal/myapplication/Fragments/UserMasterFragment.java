package com.example.rakeshvasal.myapplication.Fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.Events;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
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
public class UserMasterFragment extends BaseFragment {

    private DatabaseReference mUserDatabase,ref;
    FirebaseDatabase mFirebaseInstance;
    RecyclerView recyclerView;
    EditText search_text;
    Button btn_search,btn_add;
    public UserMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_user_master, container, false);

        search_text = (EditText) rootview.findViewById(R.id.searchtext);
        btn_add = (Button) rootview.findViewById(R.id.adduser);
        btn_search = (Button) rootview.findViewById(R.id.searchuser);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        try {
            mFirebaseInstance = FirebaseDatabase.getInstance();
            mUserDatabase = mFirebaseInstance.getReference();
            ref = mUserDatabase.child("users");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                android.app.Fragment fragment = new AddUpdateUserFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fetchallusers();
        readData();
        return rootview;
    }

    private void fetchallusers(){

        final List<User> mUserEntries = new ArrayList<>();
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        User user = eventsnapshot.getValue(User.class);
                        mUserEntries.add(user);

                    }
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(),mUserEntries);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d("usermasterdatabaseerror", databaseError.getMessage());
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readData(){
        DatabaseReference childref = ref.child("").getRef();
        ref.orderByChild("user_name").equalTo("Rakesh Vasal").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user_name = dataSnapshot.getValue(User.class);
                //System.out.println(dataSnapshot.getKey() + " was " + dinosaur.height + " meters tall.");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        childref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                    Log.d("children",dataSnapshot.getChildren().toString());
                    //User user = eventsnapshot.getValue(User.class);
                    //mUserEntries.add(user);

                }
                /*UserMasterAdapter adapter = new UserMasterAdapter(getActivity(),mUserEntries);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("usermasterdatabaseerror", databaseError.getMessage());
            }
        });
    }
}

