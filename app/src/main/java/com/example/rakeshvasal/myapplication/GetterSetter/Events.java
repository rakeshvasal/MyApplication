package com.example.rakeshvasal.myapplication.GetterSetter;


/**
 * Created by Axisvation on 11/1/2017.
 */

public class Events {

    public String eventName,location,entryFees,contactPerson,startdate,enddate,contactno;
    int id;
    boolean selectedposition;

    public Events(String eventName, String location, String entryFees, String contactPerson,String contactno, int id,String startdate,String enddate) {
        this.eventName = eventName;
        this.location = location;
        this.entryFees = entryFees;
        this.contactPerson = contactPerson;
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.contactno = contactno;
    }

    public Events() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
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

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }


}
