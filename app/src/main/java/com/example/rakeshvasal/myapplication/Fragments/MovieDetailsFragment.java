package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseFragment;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieDetailsFragment extends BaseFragment {

    String movie_id;
    private ApiInterface apiInterface;
    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.MOVIEDB_BASE_URL).create(ApiInterface.class);
        movie_id = getArguments().getString("movieid");

        Call<JsonObject> jsonObjectCall = apiInterface.getMovieDetails(movie_id,getResources().getString(R.string.MovieDB_API_KEY), "en-US");

            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    closeProgressDialog();
                    JsonObject json = response.body();

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    closeProgressDialog();

                    Log.e("movieexcep", t.toString());
                }
            });

        return root;
    }

}
