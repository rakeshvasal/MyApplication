package com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUpdateLocationFragment extends BaseFragment {

    EditText et_location_name;
    Button add;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;

    public AddUpdateLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_update_location, container, false);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("locations");
        et_location_name = (EditText) root.findViewById(R.id.location_name);
        add = (Button) root.findViewById(R.id.add_location);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation(et_location_name.getText().toString());
            }
        });

        return root;
    }

    private void addLocation(String location_name){
            try {

                String userId = mDatabase.push().getKey();

                mDatabase.child(userId).child("name").setValue(location_name);

                //mDatabase.child(userId).setValue(location_name);

                et_location_name.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


