package com.example.rakeshvasal.myapplication.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.rakeshvasal.myapplication.DbUser

interface UserDao {

    @Query("SELECT  *  FROM users")
    fun getAllUsers() : List<DbUser>

    @Query("SELECT * FROM users WHERE user_id IN (:userIds)")
    fun getUsersFromIds(userIds : IntArray) : List<DbUser>

    @Insert
    fun insertAll(vararg users: DbUser)

    @Delete
    fun delete(user: DbUser)

    @Update
    fun updateTodo(vararg users: DbUser)
}