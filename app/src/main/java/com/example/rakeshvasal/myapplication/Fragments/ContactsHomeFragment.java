package com.example.rakeshvasal.myapplication.Fragments;


import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Custom_Adapters.GoogleContactsAdapter;
import com.example.rakeshvasal.myapplication.GetterSetter.GPeople;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsHomeFragment extends BaseFragment implements GoogleContactsAdapter.OnShareClickedListener {

    final String TAG = getClass().getName();
    TextView tv_count_w_o_no, tv_count_w_no;
    JSONObject jsonObject;
    String accesstoken;
    List<GPeople> gPeoplelist = new ArrayList<>();
    public static int RC_AUTHORIZE_CONTACTS = 1;
    public static int RC_REAUTHORIZE = 2;
    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /**
     * Global instance of the JSON factory.
     */
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    Activity activity;


    public ContactsHomeFragment() {

    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contacts_home, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        tv_count_w_o_no = (TextView) root.findViewById(R.id.count_w_o_no);
        tv_count_w_no = (TextView) root.findViewById(R.id.count_w_no);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        activity = getActivity();
        SharedPreferences preferences = activity.getSharedPreferences(Utils.GOOGLE_LOGIN_DATA, MODE_PRIVATE);
        String logindets = preferences.getString("GoogleAccountDetails", "");
        if (!logindets.equalsIgnoreCase("")) {
            try {
                jsonObject = new JSONObject(logindets);
                accesstoken = jsonObject.getString("google_token");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        launchAuthDialog();

        return root;
    }

    private void launchAuthDialog() {
        Scope SCOPE_CONTACTS_READ =
                new Scope("https://www.googleapis.com/auth/contacts.readonly");
        Scope SCOPE_EMAIL = new Scope(Scopes.EMAIL);

        if (!GoogleSignIn.hasPermissions(
                GoogleSignIn.getLastSignedInAccount(activity),
                SCOPE_CONTACTS_READ,
                SCOPE_EMAIL)) {
            GoogleSignIn.requestPermissions(
                    activity,
                    RC_AUTHORIZE_CONTACTS,
                    GoogleSignIn.getLastSignedInAccount(activity),
                    SCOPE_CONTACTS_READ,
                    SCOPE_EMAIL);
        } else {
            getContacts();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (RC_AUTHORIZE_CONTACTS == requestCode) {
                getContacts();
            }
        }
    }

    private void getContacts() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (account != null) {
            GetContactsTask task = new GetContactsTask(account.getAccount());
            task.execute();
        }
    }

    @Override
    public void ShareClicked(int id) {
        GPeople gPeople = gPeoplelist.get(id);
        Gson gson = new Gson();
        //PeopleJson = gson.toJson(result);
        String gpeoplejson = gson.toJson(gPeople);
        Bundle bundle = new Bundle();
        bundle.putString("gPep", gpeoplejson);
        ContactDetailsFragment concfrag = new ContactDetailsFragment();
        concfrag.setArguments(bundle);
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment_container, concfrag);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private class GetContactsTask extends AsyncTask<Void, Void, List<Person>> {

        Account mAccount;
        String PeopleJson;

        public GetContactsTask(Account account) {
            mAccount = account;
        }

        @Override
        protected List<Person> doInBackground(Void... params) {
            List<Person> result = null;
            try {
                GoogleAccountCredential credential =
                        GoogleAccountCredential.usingOAuth2(
                                activity,
                                Collections.singleton(
                                        "https://www.googleapis.com/auth/contacts.readonly")
                        );
                credential.setSelectedAccount(mAccount);
                People service = new People.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                        .setApplicationName("myapplication-8f68b")
                        .build();

                ListConnectionsResponse connectionsResponse = service
                        .people()
                        .connections()
                        .list("people/me")
                        .set("personFields", "names,addresses,birthdays,genders,phoneNumbers,photos")
                        .execute();


                result = connectionsResponse.getConnections();
                Gson gson = new Gson();
                PeopleJson = gson.toJson(result);

            } catch (UserRecoverableAuthIOException userRecoverableException) {
                userRecoverableException.printStackTrace();

                startActivityForResult(userRecoverableException.getIntent(), RC_REAUTHORIZE);
            } catch (IOException e) {
                e.printStackTrace();
                // Other non-recoverable exceptions.
            }

            return result;
        }

        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(List<Person> connections) {
            try {
                int count_w_o_no = 0, count_w_no = 0;
                logInfo(PeopleJson);

                JSONArray JsonArray = new JSONArray(PeopleJson);
                Gson gson = new Gson();
                for (int i = 0; i < JsonArray.length(); i++) {
                    JSONObject jsonObject = JsonArray.getJSONObject(i);
                    logInfo("" + jsonObject);
                    if (jsonObject.has("names")) {
                        GPeople gPeople = gson.fromJson(String.valueOf(JsonArray.getJSONObject(i)), GPeople.class);
                        gPeoplelist.add(gPeople);
                    } else {
                        count_w_o_no++;
                    }
                    count_w_no = JsonArray.length() - count_w_o_no;
                }

                GoogleContactsAdapter adapter = new GoogleContactsAdapter(getActivity(), gPeoplelist);
                adapter.setOnShareClickedListener(ContactsHomeFragment.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tv_count_w_no.setText("Total Conatacts with Names :" + count_w_no);
                tv_count_w_o_no.setText("Total Conatacts without Names :" + count_w_o_no);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
