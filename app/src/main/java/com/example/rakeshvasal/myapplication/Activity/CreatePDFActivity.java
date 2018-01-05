package com.example.rakeshvasal.myapplication.Activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.BaseActivity;
import com.example.rakeshvasal.myapplication.Fragments.ImageAnalysisFragment;
import com.example.rakeshvasal.myapplication.Fragments.ItextFragment;
import com.example.rakeshvasal.myapplication.GetterSetter.Matches;
import com.example.rakeshvasal.myapplication.GetterSetter.TweetList;
import com.example.rakeshvasal.myapplication.GetterSetter.TwitterTokenType;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.io.UnsupportedEncodingException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rakeshvasal.myapplication.Utilities.Utils.getBase64String;

public class CreatePDFActivity extends BaseActivity {
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.TWITTER_BASE_URL).create(ApiInterface.class);
        try {
             apiInterface.getToken("Basic " + getBase64String(Utils.BEARER_TOKEN_CREDENTIALS), "client_credentials", new Callback<TwitterTokenType>() {
                @Override
                public void onResponse(Call<TwitterTokenType> call, Response<TwitterTokenType> response) {

                }

                @Override
                public void onFailure(Call<TwitterTokenType> call, Throwable t) {

                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        apiInterface.getTweetList("Bearer " + "", "", new Callback<TweetList>() {
            @Override
            public void onResponse(Call<TweetList> call, Response<TweetList> response) {

            }

            @Override
            public void onFailure(Call<TweetList> call, Throwable t) {

            }


        });
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new ItextFragment();
        transaction.add(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
