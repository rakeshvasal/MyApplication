package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 12-Nov-17.
 */

public class Locations {

    String name,locationid;

    public Locations(String locationid, String name) {
        this.locationid = locationid;
        this.name = name;
    }

    public Locations(){

    }

    public String getLocationid() {
        return locationid;
    }

    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }

    public String getLocationName() {
        return name;
    }

    public void setLocationName(String name) {
        this.name = name;
    }
}
