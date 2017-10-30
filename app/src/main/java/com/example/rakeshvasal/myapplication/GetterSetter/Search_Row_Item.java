package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by User on 9/18/2016.
 */


public class Search_Row_Item {
    String restaurant_name;
    String restaurant_id;
    String locality;


    public Search_Row_Item(String restaurant_name, String restaurant_id, String locality) {
        this.restaurant_name = restaurant_name;
        this.restaurant_id = restaurant_id;
        this.locality = locality;
    }
    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
    public String getRestaurant_id() {
        return restaurant_id;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }


}
