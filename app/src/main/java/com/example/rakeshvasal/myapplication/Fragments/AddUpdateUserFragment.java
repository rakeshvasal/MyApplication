package com.example.rakeshvasal.myapplication.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class AddUpdateUserFragment extends BaseFragment {

    EditText et_name, et_contact_no, email_id, et_branch, course_year, et_password, et_conf_password;
    Button submit;
    String photourl, googleid;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;
    FirebaseDatabase mFirebaseInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fest_register, container, false);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("users");
        sharedPreferences = getActivity().getSharedPreferences(Utils.GOOGLE_LOGIN_DATA, Context.MODE_PRIVATE);
        String isGoogleSignIn = sharedPreferences.getString("isGoogleSignedIn", "false");
        if (isGoogleSignIn.equalsIgnoreCase("true")) {
            ShowDialog();
        }
        // Inflate the activity_facebook for this fragment

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_contact_no = (EditText) view.findViewById(R.id.et_contact_no);
        email_id = (EditText) view.findViewById(R.id.email_id);
        et_branch = (EditText) view.findViewById(R.id.et_branch);
        course_year = (EditText) view.findViewById(R.id.course_year);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_conf_password = (EditText) view.findViewById(R.id.et_conf_password);
        submit = (Button) view.findViewById(R.id.submit);

        operations();

        return view;


    }

    private void operations() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name.getText().toString();
                String contact = et_contact_no.getText().toString();
                String email = email_id.getText().toString();
                String branch = et_branch.getText().toString();
                String courseyear = course_year.getText().toString();
                String password = et_password.getText().toString();

                if (googleid == null || googleid.equalsIgnoreCase("")) {
                    int id = (int) System.currentTimeMillis();
                    if (id < 0) {
                        int intgoogleid = id * -1;
                        googleid = "" + intgoogleid;
                    }
                }
                if (photourl == null) {
                    photourl = "";
                }


                User user = new User(name, email, contact, photourl, contact, branch, courseyear, password, googleid);
                AddUpdateUser(user);
            }
        });

    }

    private void AddUpdateUser(User user) {
        try {
            String userid = mDatabase.push().getKey();

            mDatabase.child(userid).setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
            shortToast("Error while inserting/updating user" + e.getMessage());
        }
    }

    public AddUpdateUserFragment() {
        //Required empty public constructor
    }

    private void ShowDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Do you wish to use Details Available on Google..?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountdetailsjson = sharedPreferences.getString("GoogleAccountDetails", "");
                Log.d("GoogleAccountDetails", accountdetailsjson);
                try {
                    JSONObject accountdetails = new JSONObject(accountdetailsjson);
                    et_name.setText(accountdetails.getString("personName"));
                    email_id.setText(accountdetails.getString("personEmail"));
                    photourl = accountdetails.getString("personPhoto");
                    googleid = accountdetails.getString("personId");
                } catch (Exception e) {
                    e.printStackTrace();
                    shortToast("Error while fetching data from Google : \n" + e.getMessage());
                }
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                shortToast("Please Enter All the Details..!!!!");
            }
        });

        alertDialog.show();
    }


}
