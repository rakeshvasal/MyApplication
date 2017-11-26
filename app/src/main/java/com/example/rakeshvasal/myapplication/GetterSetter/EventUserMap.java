package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 26-Nov-17.
 */

public class EventUserMap {

    String user_id,event_name,trans_id,map_id;


    public EventUserMap(String user_id, String event_id, String trans_id,String map_id) {
        this.user_id = user_id;
        this.event_name = event_id;
        this.trans_id = trans_id;
        this.map_id = map_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }
}
