package com.example.rakeshvasal.myapplication.Fragments.CricketAPIFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFinderFragment extends BaseFragment {

    String API_KEY = "";
    private ApiInterface apiInterface;


    public PlayerFinderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_player_finder, container, false);
        API_KEY = getResources().getString(R.string.cricapikey);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.CRIC_INFO_BASE_URL).create(ApiInterface.class);
        Call<JsonObject> matches = apiInterface.getPlayerList(API_KEY,"steve");

        try {
            showProgressDialog();
            matches.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                    closeProgressDialog();
                    JsonObject jsonObject = response.body();

                        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                        logInfo(jsonObject1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    log("onFailure",t);
                    closeProgressDialog();
                }
            });
        } catch (Exception e) {
            closeProgressDialog();
            e.printStackTrace();
        }


        return view;
    }

}
