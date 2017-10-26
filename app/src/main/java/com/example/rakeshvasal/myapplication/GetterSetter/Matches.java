package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rakeshvasal on 27-Oct-17.
 */

public class Matches {

    @SerializedName("matches")
    private List<CricketMatch> matches;

    public List<CricketMatch> getResults() {
        return matches;
    }

    public void setResults(List<CricketMatch> results) {
        this.matches = results;
    }
}
