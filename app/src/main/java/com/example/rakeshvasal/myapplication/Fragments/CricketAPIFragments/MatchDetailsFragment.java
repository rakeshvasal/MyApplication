package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.MatchDetails;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Axisvation on 10/28/2017.
 */

public class MatchDetailsFragment extends BaseFragment {

    private ApiInterface apiInterface;
    int match_id;
    TextView content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        // Inflate the activity_facebook for this fragment
        view = inflater.inflate(R.layout.fragment_match_details, container, false);

        match_id = getArguments().getInt("MATCH_ID");
        Log.d("match_id", "" + match_id);
        //apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);
        content = (TextView) view.findViewById(R.id.content);
        FetchMatchDetails();

        return view;
    }

    private void FetchMatchDetails() {

        Utils.API_KEY = getResources().getString(R.string.cricapikey);
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("apikey", Utils.API_KEY);
            jsonObject.addProperty("unique_id", "" + match_id);

            Call<JsonObject> matches = apiInterface.getScore(jsonObject);

            matches.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonObject match = response.body();
                    Gson gson = new Gson();
                    MatchDetails obj = gson.fromJson(match, MatchDetails.class);
                    obj.setMatch_id(""+match_id);
                    content.setText(obj.getScore()+ " " + obj.getTeam1());
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    logInfo("" + t);
                    shortToast("fail");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
