package com.example.rakeshvasal.myapplication;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.rakeshvasal.myapplication.ServiceCalls.MakeServiceCall;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

import java.io.IOException;

public class ContactsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        new getContacts().execute();
    }

   public class getContacts extends AsyncTask<String,String,String >{

       @Override
       protected String doInBackground(String... strings) {

           try {
               String data = new MakeServiceCall().makeGetServiceCall("https://www.google.com/m8/feeds/contacts/default/full");
               logInfo(data);
           } catch (IOException e) {
               e.printStackTrace();
           }

           return null;
       }
   }
}
