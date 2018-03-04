package com.example.rakeshvasal.myapplication.GetterSetter;

/**
 * Created by Rakeshvasal on 10-Jan-18.
 */

public class Contact {

    private int id;
    private String name;
    private String email;

    public Contact(){}

    public Contact(String name, String email) {
        super();
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
//return "Contact [id=" + id + ", name=" + name + ", email=" + email + "]";
        return name;
    }

}
