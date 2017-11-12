package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 08-Jan-17.
 */

public class Image_Items {
    private String name,time;
    private double latitude,longitude;
    private int numOfImages;
    private int thumbnail;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Image_Items(String name, String time, double latitude, double longitude) {

        this.name = name;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Image_Items(){

    }
    public Image_Items (String name,double latitude,double longitude/*, int numOfImages*/) {
        this.name = name;
        this.latitude=latitude;
        this.longitude = longitude;
        this.numOfImages = numOfImages;

    }


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

   /* public int getNumOfImages(){
        return numOfImages;
    }

    public void  setNumOfImages(int numOfImages){
        this.numOfImages=numOfImages;
    }*/

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



}
