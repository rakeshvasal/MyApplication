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
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateUserFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
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

import java.util.ArrayList;
import java.util.List;


public class UserMasterFragment extends BaseFragment {

    private DatabaseReference mUserDatabase, userref,ref,childref;
    FirebaseDatabase mFirebaseInstance;
    RecyclerView recyclerView;
    EditText search_text;
    Button btn_search, btn_add;
    FragmentManager fm;
    public UserMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_user_master, container, false);
        fm = getFragmentManager();
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
            userref = mUserDatabase.child("users");
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();
                arg.putString("task", "Add");
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new AddUpdateUserFragment();
                fragment.setArguments(arg);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });
        fetchallusers();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showProgressDialog();
                if (!search_text.getText().toString().equalsIgnoreCase("")) {
                    FetchDetailsfromUserName(search_text.getText().toString());
                }
            }
        });


        return rootview;
    }

    private void fetchallusers() {
        showProgressDialog();
        final List<User> mUserEntries = new ArrayList<>();
        try {
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {

                        User user = eventsnapshot.getValue(User.class);
                        mUserEntries.add(user);

                    }
                    closeProgressDialog();
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries,fm);
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

    private void FetchDetailsfromUserName(final String str_user_name) {
        showProgressDialog();

        ref = userref.child(str_user_name);

        childref = ref.getRef();
        final List<User> mUserEntries = new ArrayList<>();
        try {
            userref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Log.d("eventsnapshot", ""+eventsnapshot);
                        User user = eventsnapshot.getValue(User.class);
                        String user_name = user.getUser_name();
                        Log.d("user_name", ""+user_name);
                        if (user_name.contains(str_user_name)){
                            mUserEntries.add(user);
                        }
                    }
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries,fm);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    closeProgressDialog();

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
}

