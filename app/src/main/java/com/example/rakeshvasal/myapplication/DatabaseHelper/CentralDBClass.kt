package com.example.rakeshvasal.myapplication.DatabaseHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rakeshvasal.myapplication.DbUser

@Database(
        entities = [DbUser::class],
        version = 1
)

abstract class CentralDBClass : RoomDatabase() {

    companion object {
        var Instance: CentralDBClass? = null

        fun getDatabaseObject(context: Context): CentralDBClass? {

            if (Instance == null) {
                synchronized(CentralDBClass::class) {
                    Instance = Room.databaseBuilder(context.applicationContext, CentralDBClass::class.java, "CentralDb")
                            .build()
                }
            }
            return Instance
        }

    }
}