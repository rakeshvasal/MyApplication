package com.example.rakeshvasal.myapplication.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.MatchDetails;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Axisvation on 10/28/2017.
 */

public class MatchDetailsFragment extends BaseFragment {

    private ApiInterface apiInterface;
    int match_id;
    List<Matches> matchDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        // Inflate the activity_facebook for this fragment
        view = inflater.inflate(R.layout.fragment_match_details, container, false);
        match_id = getArguments().getInt("MATCH_ID");
        Log.d("match_id",""+match_id);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        // new FetchMatchDetails().execute();

        FetchMatchDetails();

        return view;
    }

    private void FetchMatchDetails() {

//wait
        Map<String,String> data = new HashMap<>();
        data.put("apikey", getResources().getString(R.string.cricapikey));
        data.put("unique_id", ""+match_id);

        try {
            Call<MatchDetails> matches = apiInterface.checkLevel(1);
            //Call<MatchDetails> matches = apiInterface.getMatchDetails(data);

            matches.enqueue(new Callback<MatchDetails>() {
                @Override
                public void onResponse(Call<MatchDetails> call, Response<MatchDetails> response) {
                    response.body();
                    List<MatchDetails> match =  response.body().getMatches();
                    //Toast.makeText()
                    //matcheslist = response.body();
                   ListtoJson(match);
                    //createlist();

                }

                @Override
                public void onFailure(Call<MatchDetails> call, Throwable t) {
                    logInfo(""+t);
                    shortToast("fail");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void ListtoJson(List<MatchDetails> match) {

        String json = new Gson().toJson(match);
        Log.d("listtojson", json);

    }





   /* public class FetchMatchDetails extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {



            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgressDialog();
        }
    }*/
}
