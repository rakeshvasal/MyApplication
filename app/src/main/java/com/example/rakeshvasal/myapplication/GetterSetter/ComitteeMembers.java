package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 12-Nov-17.
 */

public class ComitteeMembers {
    String memberName, memberID,contact_no;


    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {

        return memberName;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public ComitteeMembers(String memberName, String memberID, String contact_no) {
        this.memberName = memberName;
        this.memberID = memberID;
        this.contact_no = contact_no;
    }
}
