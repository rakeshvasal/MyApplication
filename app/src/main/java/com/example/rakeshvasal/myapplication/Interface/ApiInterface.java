package com.example.rakeshvasal.myapplication.Interface;

import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public interface ApiInterface {


    @POST("matches/")
    Call<Matches> getMatches(@Query("apikey") String apiKey);
}
