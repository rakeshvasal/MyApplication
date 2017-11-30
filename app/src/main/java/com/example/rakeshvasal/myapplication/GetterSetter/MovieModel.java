package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Axisvation on 11/30/2017.
 */

public class MovieModel {

    private String description;
    private int thumbnail;

    public MovieModel() {
    }

    public MovieModel(String description, int thumbnail) {

        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
