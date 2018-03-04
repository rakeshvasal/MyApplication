package com.example.rakeshvasal.myapplication.Activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;


import com.example.rakeshvasal.myapplication.BaseActivity;

import com.example.rakeshvasal.myapplication.Fragments.ItextFragment;

import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;


public class CreatePDFActivity extends BaseActivity {
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new ItextFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
