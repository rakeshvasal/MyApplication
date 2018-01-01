package com.example.rakeshvasal.myapplication.Interface;



import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieDataSet;
import com.example.rakeshvasal.myapplication.GetterSetter.MovieListObject;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public interface ApiInterface {

    @Headers({"Accept: application/json"})
    @POST("matches/")
    Call<Matches> getMatches(@Query("apikey") String apiKey);

    @Headers({"Accept: application/json"})
    @POST("movie/top_rated")
    Call<MovieListObject> getMovies(@Query("api_key") String apiKey, @Query("language") String language, @Query ("page") String page, @Query ("region") String region);

    @Headers({"Accept: application/json"})
    @GET("movie/{id}")
    Call<JsonObject> getMovieDetails(@Path("id") String id,@Query("api_key") String apiKey, @Query("language") String language);
    /*@POST("cricketScore/")
    Call<MatchDetails> getMatchDetails(@Query("apikey") String apiKey, @Query("unique_id") String unique_id);*/

    /*@GET("/news")
    Call<List<MatchDetails>> getMatchDetails(
            @Query("page") String page,
            @Query("order") String order,
    );*/


    @POST("cricketScore/}")
    Call<JsonObject> getScore(@Query("apikey") String apiKey,@Query("unique_id") String unique_id);

    /*@POST("cricketScore}")
    Call<String> getScore(@Body JsonObject object);
*/
    @POST("cricketScore/}")
    Call<JsonObject> getScore(@Body JsonObject object);

    @POST("matchCalendar/}")
    Call<JsonObject> getMatchCalender(@Body JsonObject object);


}
