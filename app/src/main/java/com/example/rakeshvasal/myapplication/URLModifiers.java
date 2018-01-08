package com.example.rakeshvasal.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rakeshvasal.myapplication.Custom_Adapters.FBFeedsCustomAdapter;
import com.example.rakeshvasal.myapplication.Fragments.FacebookFragments.PostsFragment;
import com.example.rakeshvasal.myapplication.ServiceCalls.MakeServiceCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Rakeshvasal on 08-Jan-18.
 */

public class URLModifiers {

    private Context context;
    private String finalvalue;
    private getValue mCallback;
    private String urlLink = "https://www.googleapis.com/urlshortener/v1/url";
    private String api_key = "AIzaSyCF4nh7DBwfFDzknN1sg5SE50bD9OybZAk";

    public URLModifiers(Context context) {
        this.context = context;
    }

    public String shortenUrl(String input) {


        urlLink = urlLink + "?key=" + api_key;

        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("longUrl", input);

            new GetData(context, URLModifiers.this, urlLink, jsonObject.toString()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalvalue;
    }

    public String ExpandUrl(String input) {

        try {
            urlLink = urlLink + "?key=" + api_key + "&shortUrl" + input;
            new ExpandUrl(context, URLModifiers.this, urlLink).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return finalvalue;


    }

    public void onBackgroundTaskCompleted(String s) {
        if (s != null) {
            if (!s.equalsIgnoreCase("")) {
                finalvalue = s;
                mCallback.setData(finalvalue);
            }
        }
    }

    public interface getValue {
        void setData(String data);

    }

    public void setValueListener(getValue mCallback) {
        this.mCallback = mCallback;
    }

    public class GetData extends AsyncTask<String, String, String> {

        String data, url, json;
        URLModifiers caller;
        Context context;
        ProgressDialog progressDialog;

        GetData(Context context, URLModifiers caller, String url, String json) {
            this.caller = caller;
            this.url = url;
            this.json = json;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Shortening Urls...Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            data = new MakeServiceCall().makeServiceCall(url, json);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            caller.onBackgroundTaskCompleted(data);
        }
    }

    public class ExpandUrl extends AsyncTask<String, String, String> {

        String data, url, json;
        URLModifiers caller;
        Context context;
        ProgressDialog progressDialog;

        ExpandUrl(Context context, URLModifiers caller, String url) {
            this.caller = caller;
            this.url = url;
            this.json = json;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Expanding Urls...Please Wait");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                data = new MakeServiceCall().makeServiceCall(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            caller.onBackgroundTaskCompleted(data);
        }
    }
}
