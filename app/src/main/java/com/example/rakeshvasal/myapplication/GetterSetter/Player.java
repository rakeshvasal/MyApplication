package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Axisvation on 1/3/2018.
 */

public class Player  {
    @SerializedName("pid")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("fullName")
    private String fullname;
    @SerializedName("profile")
    private String profile;
    @SerializedName("battingStyle")
    private String battingStyle;
    @SerializedName("bowlingStyle")
    private String bowlingStyle;
    @SerializedName("currentAge")
    private String currentAge;
    @SerializedName("born")
    private String born;
    @SerializedName("country")
    private String country;
    @SerializedName("playingRole")
    private String playingRole;
    @SerializedName("bowling")
    private String bowling;
    @SerializedName("imageURL")
    private String imageURL;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public String getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(String currentAge) {
        this.currentAge = currentAge;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlayingRole() {
        return playingRole;
    }

    public void setPlayingRole(String playingRole) {
        this.playingRole = playingRole;
    }

    public String getBowling() {
        return bowling;
    }

    public void setBowling(String bowling) {
        this.bowling = bowling;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
