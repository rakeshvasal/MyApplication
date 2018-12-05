package com.example.rakeshvasal.myapplication.Fragments.MasterFragments;


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
import com.example.rakeshvasal.myapplication.Custom_Adapters.CommitteeMemberAdapter;
import com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments.AddUpdateCommiteeMember;
import com.example.rakeshvasal.myapplication.GetterSetter.ComitteeMembers;
import com.example.rakeshvasal.myapplication.R;
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
public class CommitteeMemberMasterFragment extends BaseFragment {

    Button add,search;
    EditText search_text;
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;
    DatabaseReference ref;

    public CommitteeMemberMasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_comittee_member_master, container, false);


        add = (Button) rootview.findViewById(R.id.add);
        search = (Button) rootview.findViewById(R.id.search);
        search_text = (EditText) rootview.findViewById(R.id.searchtext);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("committee_members");

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        showProgressDialog();
        fetchallmembers();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                android.app.Fragment fragment = new AddUpdateCommiteeMember();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return rootview;
    }

    private void fetchallmembers(){

        final List<ComitteeMembers> mUserEntries = new ArrayList<>();
        try {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot eventsnapshot : dataSnapshot.getChildren()) {
                        closeProgressDialog();
                        ComitteeMembers members = eventsnapshot.getValue(ComitteeMembers.class);
                        mUserEntries.add(members);

                    }
                    closeProgressDialog();
                    CommitteeMemberAdapter adapter = new CommitteeMemberAdapter( mUserEntries,getActivity());
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

}
