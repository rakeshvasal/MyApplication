package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 01-Jan-18.
 */

public class CalenderMatch {

    @SerializedName("unique_id")
    private String unique_id;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
