package com.example.rakeshvasal.myapplication.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rakeshvasal.myapplication.DbUser;
import com.example.rakeshvasal.myapplication.GetterSetter.User;

import java.util.ArrayList;

@Dao
public interface UserDao {

    @Query("Select * from users")
    ArrayList<DbUser> getAllUsers();

    @Insert
    void addUser(DbUser user);

    @Query("Select * from users where user_id = :id")
    User getUserById(String id);

    @Query("Select * from users where user_name = :name")
    User getUserByName(String name);

    @Query("DELETE from users")
    User DeleteAllUsers();

    @Query("DELETE from users where user_id = :id")
    User DeleteUserById(String id);
}
