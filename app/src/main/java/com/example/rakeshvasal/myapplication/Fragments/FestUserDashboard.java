package com.example.rakeshvasal.myapplication.Fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.Fragments.MasterFragments.UserMasterFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class FestUserDashboard extends BaseFragment {

    TextView events_participate,events_calender,myprofile;
    ImageView iv_profile_image;
    String user_id;
    private DatabaseReference mUserDatabase, userref,ref,childref;
    FirebaseDatabase mFirebaseInstance;
    List<User> mUserEntries = new ArrayList<>();
    String json;
    FragmentManager fm;
    public FestUserDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the activity_facebook for this fragment
        View root = inflater.inflate(R.layout.fragment_fest_user_dashboard, container, false);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        userref = mFirebaseInstance.getReference("users");
        events_calender = (TextView) root.findViewById(R.id.events_calender);
        events_participate = (TextView) root.findViewById(R.id.event_participate);
        myprofile = (TextView) root.findViewById(R.id.user_profile);
        iv_profile_image  = (ImageView) root.findViewById(R.id.iv_profile_image);
        fm= getFragmentManager();
        user_id = getArguments().getString("user_id");
        fetchDetailsfromUserId(user_id);
        events_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        events_participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arg = new Bundle();
                arg.putString("user_id", user_id);
                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                Fragment fragment = new EventParticipateFragment();
                fragment.setArguments(arg);
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserEntries!=null){
                    if (mUserEntries.size()>0){

                        Bundle bundle = new Bundle();
                        bundle.putString("userjson",json);
                        MyProfileFragment myprofile= new MyProfileFragment();
                        myprofile.setArguments(bundle);
                        myprofile.show(fm,"My Profile");

                    }
                }
            }
        });

        return root;
    }


    private void fetchDetailsfromUserId(final String str_user_id) {
        showProgressDialog();

        ref = userref.child(str_user_id);

        childref = ref.getRef();

        try {
            userref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        Log.d("eventsnapshot", ""+eventsnapshot);
                        User user = eventsnapshot.getValue(User.class);
                        String user_id = user.getUser_id();
                        Log.d("user_name", ""+user_id);
                        if (user_id.contains(str_user_id)){
                            mUserEntries.add(user);
                            json = new Gson().toJson(mUserEntries);

                        }
                    }
                    //shortToast(mUserEntries.get(0).getUser_name());
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
