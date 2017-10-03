package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Fragments.FestAdminDashboard;
import com.example.rakeshvasal.myapplication.Fragments.FestRegisterFragment;

import com.example.rakeshvasal.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Axisvation on 9/8/2017.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home,container,false);

        ButterKnife.bind(this, root);




        return root;
    }

    private void operations(){

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment fragment = new FestRegisterFragment();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser();

            }
        });
    }

    private void validateUser(){

        String role="";
        if(role.equalsIgnoreCase("Admin")) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment fragment = new FestAdminDashboard();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment fragment = new FestAdminDashboard();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

    }

}
