package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 08-Jan-17.
 */

public class Image_Items {
    private String name;
    private String time;
    private String download_url;
    private double latitude,longitude;
    private int numOfImages;
    private int thumbnail;

    public Image_Items(String name, String time, String download_url, double latitude, double longitude) {
        this.name = name;
        this.time = time;
        this.download_url = download_url;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Image_Items(String name, String time, double latitude, double longitude) {

        this.name = name;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Image_Items (String name,double latitude,double longitude/*, int numOfImages*/) {
        this.name = name;
        this.latitude=latitude;
        this.longitude = longitude;
        this.numOfImages = numOfImages;

    }

    public Image_Items(){

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
