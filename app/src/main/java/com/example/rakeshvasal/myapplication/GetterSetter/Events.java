package com.example.rakeshvasal.myapplication.GetterSetter;


/**
 * Created by Axisvation on 11/1/2017.
 */

public class Events {

    public String eventName,location,entryFees,contactPerson;
    public Events() {
    }
    public Events(String eventName,String location,String entryFees,String contactPerson) {
        this.eventName = eventName;
        this.location=location;
        this.entryFees=entryFees;
        this.contactPerson=contactPerson;
    }

    public String getEventName() {
        return eventName;
    }

    public String getLocation() {
        return location;
    }

    public String getEntryFees() {
        return entryFees;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEntryFees(String entryFees) {
        this.entryFees = entryFees;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
