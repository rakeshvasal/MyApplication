package com.example.rakeshvasal.myapplication.GetterSetter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rakeshvasal on 26-Oct-17.
 */

public class Contact {
    @SerializedName("")
    private String contactName;

    private int mobNumber;

    public void setName(String contactName) {
        contactName = contactName;
    }

    public void getmobNumber(int mobNumber) {
        this.mobNumber = mobNumber;
    }

    public String getName() {

        return contactName;
    }

    public int setmobNumber() {
        return mobNumber;
    }
}
