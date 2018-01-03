package com.example.rakeshvasal.myapplication.Fragments.AddUpdateFragments;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.UserMasterAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddUpdateUserFragment extends BaseFragment {

    EditText et_name, et_contact_no, email_id, et_branch, course_year, et_password, et_conf_password;
    Button submit;
    String photourl, googleid, user_id="", task,pass;
    SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase, ref, childref,member_ref;
    FirebaseDatabase mFirebaseInstance;
    RadioGroup role_group;
    RadioButton rb_stu, rb_admin;
    LinearLayout ll_role, ll_pass, ll_conf_pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fest_register, container, false);


        // Inflate the activity_facebook for this fragment

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_contact_no = (EditText) view.findViewById(R.id.et_contact_no);
        email_id = (EditText) view.findViewById(R.id.email_id);
        et_branch = (EditText) view.findViewById(R.id.et_branch);
        course_year = (EditText) view.findViewById(R.id.course_year);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_conf_password = (EditText) view.findViewById(R.id.et_conf_password);
        submit = (Button) view.findViewById(R.id.submit);
        role_group = (RadioGroup) view.findViewById(R.id.role_group);
        rb_stu = (RadioButton) view.findViewById(R.id.rb_student);
        rb_admin = (RadioButton) view.findViewById(R.id.rb_admin);
        ll_role = (LinearLayout) view.findViewById(R.id.ll_role);
        ll_pass = (LinearLayout) view.findViewById(R.id.ll_pass);
        ll_conf_pass = (LinearLayout) view.findViewById(R.id.ll_conf_pass);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseInstance.getReference("users");
        member_ref = mFirebaseInstance.getReference("committee_members");
        sharedPreferences = getActivity().getSharedPreferences(Utils.GOOGLE_LOGIN_DATA, Context.MODE_PRIVATE);
        task = getArguments().getString(Utils.TASK);
        if (task.equalsIgnoreCase(Utils.UPDATE_TASK)) {
            submit.setText("Update");
        } else {
            submit.setText("Add");
        }
        user_id = getArguments().getString("userid");
        if (user_id == null || user_id.equalsIgnoreCase("")) {
            ll_role.setVisibility(View.GONE);
            String isGoogleSignIn = sharedPreferences.getString("isGoogleSignedIn", "false");
            if (isGoogleSignIn.equalsIgnoreCase("true")) {

                ShowDialog();
            }
        } else {
            FetchDetailsfromUserId(user_id);
        }
        operations(view);
        return view;


    }

    private void operations(final View view1) {

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
                int id = role_group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) view1.findViewById(id);
                String role = radioButton.getText().toString();


                User user = new User(name, email, user_id, photourl, contact, branch, courseyear, password, googleid, role);
                AddUpdateUser(user/*,param,value*/);
            }
        });

    }

    private void AddUpdateUser(User user/*,String param,String value*/) {

        if (task.equalsIgnoreCase("Update")) {
            String userid = user.getUser_id();
            user.setPassword(pass);
            user.setPhotourl(photourl);
            user.setUser_id(userid);
            mDatabase.child(userid).setValue(user);

           // childref = ref.getRef();
        } else {
            try {
                String userid = mDatabase.push().getKey();
                user.setUser_id(userid);
                mDatabase.child(userid).setValue(user);
            } catch (Exception e) {
                e.printStackTrace();
                shortToast("Error while inserting/updating user" + e.getMessage());
            }
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

    private void FetchDetailsfromUserId(String user_id) {
        showProgressDialog();
        ref = mDatabase.child(user_id);
        childref = ref.getRef();
        final List<User> mUserEntries = new ArrayList<>();
        try {
            childref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User user = dataSnapshot.getValue(User.class);
                    mUserEntries.add(user);
                    String json = new Gson().toJson(mUserEntries);
                    Log.d("totaljson", json);
                    closeProgressDialog();
                    try {
                        JSONArray jsonArray = new JSONArray(json);
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            Log.d("jsonobject", "" + jsonObject);
                            setValues("" + jsonObject);
                        } catch (Exception r) {
                            r.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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

    private void setValues(String jsonObject) {
        JsonParser parser = new JsonParser();
        JsonElement mJson = parser.parse(jsonObject);
        Gson gson = new Gson();
        User object = gson.fromJson(mJson, User.class);
        String role = object.getRole();
        if (role.equalsIgnoreCase("Student")){
            ll_role.setVisibility(View.GONE);
        }
        et_name.setText(object.getUser_name());
        et_contact_no.setText(object.getContact_no());
        email_id.setText(object.getUser_email());
        course_year.setText(object.getCourse_year());
        et_branch.setText(object.getBranch());
        ll_pass.setVisibility(View.GONE);
        ll_conf_pass.setVisibility(View.GONE);
        photourl=object.getPhotourl();
        pass=object.getPassword();


    }


}
