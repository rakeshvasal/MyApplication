package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Axisvation on 12/2/2017.
 */

public class MovieDataSet {
    @SerializedName("original_title")
    private String name;
    @SerializedName("vote_average")
    private String rating;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterurl;
    /*@SerializedName("team-1")
    @SerializedName("team-1")
    @SerializedName("team-1")
    @SerializedName("team-1")
    @SerializedName("team-1")
    @SerializedName("team-1")*/

    public MovieDataSet(String name, String rating, String id, String title, String posterurl) {
        this.name = name;
        this.rating = rating;
        this.id = id;
        this.title = title;
        this.posterurl = posterurl;
    }


    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieDataSet() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


}
