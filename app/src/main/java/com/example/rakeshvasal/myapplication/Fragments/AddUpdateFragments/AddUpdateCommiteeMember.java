package com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.Fragments.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.ComitteeMembers;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUpdateCommiteeMember extends BaseFragment {

    EditText et_name, et_contact_no;
    Button submit;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;

    public AddUpdateCommiteeMember() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_add_update_commitee_member, container, false);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("committee_members");
        et_name = (EditText) rootview.findViewById(R.id.et_name);
        et_contact_no = (EditText) rootview.findViewById(R.id.et_contact_no);
        submit = (Button) rootview.findViewById(R.id.submit);

        OnClickListeners();

        return rootview;
    }

    private void OnClickListeners() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String contact = et_contact_no.getText().toString();

                ComitteeMembers comitteeMembers = new ComitteeMembers(name, "", contact);

                AddUpdate(comitteeMembers);
            }
        });

    }

    private void AddUpdate(ComitteeMembers members) {
        /*try {
            CentralApiCenter.getInstance().addCommiteeMember(members, new CentralCallbacks() {
                @Override
                public void onSuccess(Object response) {
                    if (response != null) {
                        et_contact_no.setText("");
                        et_name.setText("");
                    }
                }

                @Override
                public void onFailure(UIError error) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
