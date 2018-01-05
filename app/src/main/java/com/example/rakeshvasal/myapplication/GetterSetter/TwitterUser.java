package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 06-Jan-18.
 */

public class TwitterUser {
    @SerializedName("screen_name")
    public String screenName;

    @SerializedName("name")
    public String name;

    @SerializedName("profile_image_url")
    public String profileImageUrl;
}
