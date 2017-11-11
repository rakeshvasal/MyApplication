package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 12-Nov-17.
 */

public class ComitteeMembers {
    String memberName;
    int memberID;

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {

        return memberName;
    }

    public int getMemberID() {
        return memberID;
    }

    public ComitteeMembers(String memberName, int memberID) {

        this.memberName = memberName;
        this.memberID = memberID;
    }
}
