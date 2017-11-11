package com.example.rakeshvasal.myapplication.Fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommitteeMemberMasterFragment extends Fragment {

    Button add,search;
    EditText search_text;
    RecyclerView recyclerView;
    private DatabaseReference mEventsDatabase;
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
        mEventsDatabase = mFirebaseInstance.getReference();

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                android.app.Fragment fragment = new AddUpdateEventFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return rootview;
    }

}
