package com.example.rakeshvasal.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class DbUser {
    public @PrimaryKey
    String user_id;
    public String user_name;
    public String user_email;
    public String photourl;
    public String contact_no;
    public String branch;
    public String course_year;
    public String password;
    public String googleid;
    public String role;

    public DbUser(String user_id, String user_name, String user_email, String password, String googleid) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
        this.googleid = googleid;
    }

    public DbUser() {
    }
}
