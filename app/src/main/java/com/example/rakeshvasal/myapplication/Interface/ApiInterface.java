package com.example.rakeshvasal.myapplication.Interface;

import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.MatchDetails;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public interface ApiInterface {


    @POST("matches/")
    Call<Matches> getMatches(@Query("apikey") String apiKey);

    /*@POST("cricketScore/")
    Call<MatchDetails> getMatchDetails(@Query("apikey") String apiKey, @Query("unique_id") String unique_id);*/

    /*@GET("/news")
    Call<List<MatchDetails>> getMatchDetails(
            @Query("page") String page,
            @Query("order") String order,
    );*/


    @POST("cricketScore/")
    Call<MatchDetails> checkLevel(@Field("id") int id);



}
