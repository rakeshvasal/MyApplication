package com.example.rakeshvasal.myapplication.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.rakeshvasal.myapplication.GetterSetter.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("Select * from users")
    List<User> getAllUsers();

    @Insert
    void addUser(User user);

    @Query("Select * from users where user_id = :id")
    User getUserById(String id);

    @Query("Select * from users where user_name = :name")
    User getUserByName(String name);

    @Query("DELETE from users")
    void DeleteAllUsers();

    @Query("DELETE from users where user_id = :id")
    void DeleteUserById(String id);
}
