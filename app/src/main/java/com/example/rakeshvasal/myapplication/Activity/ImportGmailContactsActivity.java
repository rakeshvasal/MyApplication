package com.example.rakeshvasal.myapplication.Activity;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;


import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Custom_Adapters.GoogleContactsAdapter;
import com.example.rakeshvasal.myapplication.Fragments.ContactsHomeFragment;
import com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments.CricAPIHome;
import com.example.rakeshvasal.myapplication.Fragments.FacebookFragments.FacebookFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.GPeople;
import com.example.rakeshvasal.myapplication.GetterSetter.MatchDetails;

import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.fasterxml.jackson.core.JsonFactory;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

public class ImportGmailContactsActivity extends BaseActivity {

    JSONObject jsonObject;
    String accesstoken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_gmail_contacts);

        SharedPreferences preferences = getSharedPreferences(Utils.GOOGLE_LOGIN_DATA, MODE_PRIVATE);
        String logindets = preferences.getString("GoogleAccountDetails", "");
        if (!logindets.equalsIgnoreCase("")) {
            try {
                jsonObject = new JSONObject(logindets);
                accesstoken = jsonObject.getString("google_token");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ContactsHomeFragment cricfrag = new ContactsHomeFragment();
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fragment_container, cricfrag);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
