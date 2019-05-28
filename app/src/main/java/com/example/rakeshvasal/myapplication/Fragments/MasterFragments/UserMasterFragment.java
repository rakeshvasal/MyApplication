package com.example.rakeshvasal.myapplication.Fragments.MasterFragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.AppExecutors;
import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.DatabaseHelper.RoomDbClass;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateUserFragment;
import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.Interface.CentralCallbacks;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.ServiceCalls.CentralApiCenter;
import com.example.rakeshvasal.myapplication.UIError;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class UserMasterFragment extends BaseFragment {

    private DatabaseReference mUserDatabase, userref, ref, childref;
    FirebaseDatabase mFirebaseInstance;
    RecyclerView recyclerView;
    EditText search_text;
    Button btn_search, btn_add;
    FragmentManager fm;
    List<User> mUserEntries = new ArrayList<>();
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
                arg.putString(Utils.TASK, Utils.ADD_TASK);
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
                if (search_text.getText().toString().equalsIgnoreCase("")) {
                    fetchallusers();
                } else {
                    fetchDetailsfromUserName(search_text.getText().toString());
                }
            }
        });


        return rootview;
    }

    private void fetchallusers() {
        //showProgressDialog();

        try {


            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final RoomDbClass dbClass = RoomDbClass.getRoomDbInstance(getActivity());
                    mUserEntries = dbClass.userDao().getAllUsers();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries, fm);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            });


           /* CentralApiCenter.getInstance().getAllUsers(new CentralCallbacks() {
                @Override
                public void onSuccess(Object response) {
                   *//* List<User> mUserEntries = new ArrayList<>();
                    mUserEntries = (List<User>) response;
                    closeProgressDialog();
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries, fm);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*//*
                }

                @Override
                public void onFailure(UIError error) {
                    closeProgressDialog();
                }
            });*/
           /* userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {

                        User user = eventsnapshot.getValue(User.class);
                        mUserEntries.add(user);

                    }
                    closeProgressDialog();
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries, fm);
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

    private void fetchDetailsfromUserName(final String str_user_name) {
        showProgressDialog();
        try {
            CentralApiCenter.getInstance().getUserDetails(str_user_name, new CentralCallbacks() {
                @Override
                public void onSuccess(Object response) {
                    List<User> mUserEntries = (List<User>) response;
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries, fm);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    closeProgressDialog();
                }

                @Override
                public void onFailure(UIError error) {
                    closeProgressDialog();
                }
            });

        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }

        /*final List<User> mUserEntries = new ArrayList<>();
        try {
            userref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Log.d("eventsnapshot", "" + eventsnapshot);
                        User user = eventsnapshot.getValue(User.class);
                        String user_name = user.getUser_name();
                        Log.d("user_name", "" + user_name);
                        if (user_name.contains(str_user_name)) {
                            mUserEntries.add(user);
                        }
                    }
                    UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mUserEntries, fm);
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
        }*/
    }

    private void readData(String parameter, String searchtext) {
        showProgressDialog();
        final List<User> mEventsEntries = new ArrayList<>();
        userref.orderByChild(parameter).startAt(searchtext).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User events = dataSnapshot.getValue(User.class);
                mEventsEntries.add(events);
                closeProgressDialog();
                UserMasterAdapter adapter = new UserMasterAdapter(getActivity(), mEventsEntries, fm);
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

