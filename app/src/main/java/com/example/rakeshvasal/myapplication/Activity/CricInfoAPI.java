package com.example.rakeshvasal.myapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.GetterSetter.CricketMatch;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CricInfoAPI extends AppCompatActivity {

    private ApiInterface apiInterface;
    private Matches matcheslist;
    String API_KEY = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contat_api);
        // Inflate the layout for this fragment
        API_KEY = getResources().getString(R.string.cricapikey);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY", Toast.LENGTH_LONG).show();
            return;
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Matches> matches = apiInterface.getMatches(API_KEY);

        Log.d("match", matches.toString());

        try {
            matches.enqueue(new Callback<Matches>() {
                @Override
                public void onResponse(Call<Matches> call, Response<Matches> response) {
                    matcheslist = response.body();
                }

                @Override
                public void onFailure(Call<Matches> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
