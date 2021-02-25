package com.example.rakeshvasal.myapplication.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.rakeshvasal.myapplication.GetterSetter.CalenderEvent

@Dao
interface CalenderEventDao {

    @Query("SELECT * FROM calenderevent")
    fun getAll(): List<CalenderEvent>

    @Insert
    fun insertAll(vararg calenderEvent: CalenderEvent)

    @Delete
    fun delete(user: CalenderEvent)

    /*@Query("SELECT * FROM calenderevent")
    fun findByName(first: String, last: String): CalenderEvent*/
}