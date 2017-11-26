package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 26-Nov-17.
 */

public class Transactions {

    String trans_id,user_id;
    int amount;

    public Transactions(String user_id, String trans_id, int amount) {
        this.user_id = user_id;
        this.trans_id = trans_id;
        this.amount = amount;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
