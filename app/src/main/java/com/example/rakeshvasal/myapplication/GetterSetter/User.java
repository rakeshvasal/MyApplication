package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 06-Nov-17.
 */

public class User {

    String user_name,user_email,user_id,photourl,contact_no,branch,course_year,password,googleid,role;

    public User(){

    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getUser_name() {

        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCourse_year(String course_year) {
        this.course_year = course_year;
    }

    public String getContact_no() {

        return contact_no;
    }

    public String getBranch() {
        return branch;
    }

    public String getCourse_year() {
        return course_year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }

    public String getGoogleid() {

        return googleid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String user_name, String user_email, String user_id, String photourl, String contact_no, String branch, String course_year, String password, String googleid, String role) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_id = user_id;
        this.photourl = photourl;
        this.contact_no = contact_no;
        this.branch = branch;
        this.course_year = course_year;
        this.password = password;
        this.googleid = googleid;
        this.role = role;
    }
}
