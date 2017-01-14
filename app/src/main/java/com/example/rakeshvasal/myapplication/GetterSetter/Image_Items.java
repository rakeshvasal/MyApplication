package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 08-Jan-17.
 */

public class Image_Items {
    private String name;
    private double latitude,longitude;
    private int numOfImages;
    private int thumbnail;
    public Image_Items(){

    }
    public Image_Items (String name/*,double latitude,double longitude, int numOfImages, int thumbnail*/) {
        this.name = name;
        this.latitude=latitude;
        this.longitude = longitude;
        this.numOfImages = numOfImages;
        this.thumbnail = thumbnail;
    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public int getNumOfImages(){
        return numOfImages;
    }

    public void  setNumOfImages(int numOfImages){
        this.numOfImages=numOfImages;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
