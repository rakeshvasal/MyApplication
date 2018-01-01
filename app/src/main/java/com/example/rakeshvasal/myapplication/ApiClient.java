package com.example.rakeshvasal.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public class ApiClient {

    public static String CRIC_INFO_BASE_URL = "http://cricapi.com/api/";

    public static Retrofit retrofit = null;


    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(CRIC_INFO_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
    /*public static Retrofit getMovieDBApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(MOVIE_DB_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }*/


    public static Retrofit changeBaseURL(String newBaseURL) {
        {
            if (retrofit != null) {
                retrofit = new Retrofit.Builder().baseUrl(newBaseURL).addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;
        }
    }
}
