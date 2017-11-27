package com.example.rakeshvasal.myapplication.GetterSetter;


/**
 * Created by Axisvation on 11/1/2017.
 */

public class Events {

    public String eventName,location,entryFees,contactPerson;
    int id;



    boolean selectedposition;

    public Events(String eventName, String location, String entryFees, String contactPerson, int id) {
        this.eventName = eventName;
        this.location = location;
        this.entryFees = entryFees;
        this.contactPerson = contactPerson;
        this.id = id;
    }

    public Events() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public Events(String eventName, String location, String entryFees, String contactPerson) {
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

    public boolean getSelectedposition() {
        return selectedposition;
    }

    public void setSelectedposition(boolean selectedposition) {
        this.selectedposition = selectedposition;
    }
}
