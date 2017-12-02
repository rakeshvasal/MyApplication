package com.example.rakeshvasal.myapplication.GetterSetter;

import java.util.List;

/**
 * Created by Axisvation on 12/2/2017.
 */

public class MovieDataSet {

    private String name,rating;

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
