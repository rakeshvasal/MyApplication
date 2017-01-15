package com.example.rakeshvasal.myapplication.RowItems;

/**
 * Created by User on 9/18/2016.
 */
public class Select_restaurant_name_popup_RowItem {
    private String restaurant_name;
    private String restaurant_locality;


    public Select_restaurant_name_popup_RowItem(String restaurant_name, String restaurant_locality) {

        this.restaurant_name = restaurant_name;
        this.restaurant_locality =restaurant_locality;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_locality() {
        return restaurant_locality;
    }

    public void setRestaurant_locality(String restaurant_locality) {
        this.restaurant_locality = restaurant_locality;
    }


}
