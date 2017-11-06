package com.example.rakeshvasal.myapplication.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Fragments.MatchDetailsFragment;
import com.example.rakeshvasal.myapplication.R;

public class MatchDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int match_id = bundle.getInt("match_id", 0);
        Log.d("match_id",""+match_id);

        if (match_id != 0) {
            Bundle arg = new Bundle();
            arg.putInt("MATCH_ID", match_id);
            MatchDetailsFragment matchdetails = new MatchDetailsFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            matchdetails.setArguments(arg);
            transaction.add(R.id.fragment_container, matchdetails);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}