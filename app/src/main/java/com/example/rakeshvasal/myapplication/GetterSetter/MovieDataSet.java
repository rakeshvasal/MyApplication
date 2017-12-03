package com.example.rakeshvasal.myapplication.GetterSetter;

import java.util.List;

/**
 * Created by Axisvation on 12/2/2017.
 */

public class MovieDataSet {

    private String name;
    private String rating;
    private String id;
    private String title;

    public String getPosterurl() {
        return posterurl;
    }

    public void setPosterurl(String posterurl) {
        this.posterurl = posterurl;
    }

    private String posterurl;

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

    public MovieDataSet(String name, String rating) {

        this.name = name;
        this.rating = rating;
    }
}
