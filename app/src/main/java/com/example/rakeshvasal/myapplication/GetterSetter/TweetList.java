package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Rakeshvasal on 06-Jan-18.
 */

public class TweetList  {

    @SerializedName("statuses")
    public ArrayList<Tweet> tweets;

    public TweetList(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }
}