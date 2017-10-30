package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 29-Oct-17.
 */

public class Match {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Level")
    @Expose
    private String level;
}
