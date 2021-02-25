package com.example.rakeshvasal.myapplication.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rakeshvasal.myapplication.ApiClient;
import com.example.rakeshvasal.myapplication.GetterSetter.Tweet;
import com.example.rakeshvasal.myapplication.GetterSetter.TweetList;
import com.example.rakeshvasal.myapplication.GetterSetter.TwitterTokenType;
import com.example.rakeshvasal.myapplication.Interface.ApiInterface;
import com.example.rakeshvasal.myapplication.R;
import com.example.rakeshvasal.myapplication.Utilities.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterSearchFragment extends BaseFragment {


    public TwitterSearchFragment() {
        // Required empty public constructor
    }

    private ApiInterface apiInterface;
    List<Tweet> tweetList;
    String accessToken;

    EditText text_search;
    Button btn_go;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_twitter_search, container, false);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterface = ApiClient.changeBaseURL(Utils.TWITTER_BASE_URL).create(ApiInterface.class);

        getToken();

        text_search = (EditText) view.findViewById(R.id.search_text);
        btn_go = (Button) view.findViewById(R.id.search_go_btn);


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = text_search.getText().toString();
                if (!s.equalsIgnoreCase("")) {
                    if (!accessToken.equalsIgnoreCase("")) {
                        getTweets(accessToken, s);
                    } else {
                        shortToast("Unauthorised Access");
                    }
                } else {
                    shortToast("Enter Text");
                }
            }
        });
        return view;
    }

    private void getToken() {
        String apikey = getResources().getString(R.string.twitter_api_key);
        String privatekey = getResources().getString(R.string.twitter_secret_api_key);
        String combined = apikey + ":" + privatekey;

        String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

        Call<TwitterTokenType> token = apiInterface.getToken("Basic " + base64Encoded, "client_credentials");

        token.enqueue(new Callback<TwitterTokenType>() {
            @Override
            public void onResponse(Call<TwitterTokenType> call, Response<TwitterTokenType> response) {
                TwitterTokenType twitterTokenType = response.body();
                Log.i("accestoken", "" + (twitterTokenType != null ? twitterTokenType.getAccessToken() : ""));
                accessToken = twitterTokenType != null ? twitterTokenType.getAccessToken() : "";
                Log.i("accesstype", "" + (twitterTokenType != null ? twitterTokenType.getTokenType() : ""));

            }

            @Override
            public void onFailure(Call<TwitterTokenType> call, Throwable t) {
                Log.i("fail", t.toString());
            }
        });
    }

    private void getTweets(String accesskey, String text_string) {

        accesskey = "Bearer " + accesskey;
        Call<TweetList> tweets = apiInterface.getTweetList(accesskey, text_string);

        tweets.enqueue(new Callback<TweetList>() {
            @Override
            public void onResponse(Call<TweetList> call, Response<TweetList> response) {
                tweetList = response.body().getTweets();
                for (int i = 0; i < tweetList.size(); i++) {
                    Tweet tweet = tweetList.get(i);
                    log(tweet.text);
                }
            }

            @Override
            public void onFailure(Call<TweetList> call, Throwable t) {
                log("exception message in twitter search" ,t);
            }
        });
    }
}
