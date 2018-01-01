package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rakeshvasal on 30-Dec-17.
 */

public class MovieListObject {

    @SerializedName("results")
    private List<MovieDataSet> movieDataSets;

    public List<MovieDataSet> getMovieDataSets() {
        return movieDataSets;
    }

    public void setMovieDataSets(List<MovieDataSet> movieDataSets) {
        this.movieDataSets = movieDataSets;
    }
}
