package com.example.rakeshvasal.myapplication.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.User;
import com.example.rakeshvasal.myapplication.R;

public class FestRegisterFragment extends BaseFragment {

    EditText et_name,et_contact_no,email_id,et_branch,course_year,et_password,et_conf_password;
    Button submit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fest_register, container, false);
        // Inflate the activity_facebook for this fragment

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_contact_no  = (EditText) view.findViewById(R.id.et_contact_no);
        email_id = (EditText) view.findViewById(R.id.email_id);
        et_branch= (EditText) view.findViewById(R.id.et_branch);
        course_year = (EditText) view.findViewById(R.id.course_year);
        et_password = (EditText) view.findViewById(R.id.et_password);
        et_conf_password = (EditText) view.findViewById(R.id.et_conf_password);
        submit  = (Button) view.findViewById(R.id.submit);

        operations();

        return view;


    }

    private void operations(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name.getText().toString();
                String contact =et_contact_no.getText().toString();
                String email = email_id.getText().toString();
                String branch = et_branch.getText().toString();
                String courseyear = course_year.getText().toString();
                String password = et_password.getText().toString();

                User user = new User(name,email,"","",contact,branch,courseyear,password);
            }
        });

    }

    public FestRegisterFragment() {
        // Required empty public constructor
    }










}
