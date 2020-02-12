package com.example.rakeshvasal.myapplication.DatabaseHelper

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.rakeshvasal.myapplication.Dao.CalenderEventDao
import com.example.rakeshvasal.myapplication.GetterSetter.CalenderEvent

@Database(entities = arrayOf(CalenderEvent::class), version = 1)
abstract class EventRoomDbClass : RoomDatabase() {

    abstract fun calenderEventDao(): CalenderEventDao

    private var instance: EventRoomDbClass? = null

    fun getInstance(context: Context): EventRoomDbClass? {
        if (instance == null) {
            synchronized(EventRoomDbClass::class) {
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventRoomDbClass::class.java, "notes_database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
            }
        }
        return instance
    }

    private val roomCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomCallback","OnCreate")
        }
    }
}