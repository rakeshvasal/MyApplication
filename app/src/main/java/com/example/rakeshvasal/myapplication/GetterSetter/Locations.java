package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 12-Nov-17.
 */

public class Locations {
    int locationid;
    String locationName;

    public Locations(int locationid, String locationName) {
        this.locationid = locationid;
        this.locationName = locationName;
    }

    public Locations(){

    }

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
