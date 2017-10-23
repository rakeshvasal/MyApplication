package com.example.rakeshvasal.myapplication.GetterSetter;

import android.graphics.Bitmap;

/**
 * Created by Rakeshvasal on 23-Oct-17.
 */

public class Album {
    private String name;
    private int numOfSongs;
    private String thumbnail;

    public Album() {
    }

    /*public Album(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }*/

    public Album(String name,String thumbnail) {
        this.name = name;

        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}