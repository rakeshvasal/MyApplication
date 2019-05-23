package com.example.rakeshvasal.myapplication.DatabaseHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.rakeshvasal.myapplication.Dao.UserDao
import com.example.rakeshvasal.myapplication.DbUser


@Database(
        entities = [DbUser::class],
        version = 1
)
abstract class CentralKotlinDBClass : RoomDatabase() {
    abstract fun UserDao(): UserDao



    companion object {
        var dBName: String = "kotlinDb"
        var instance: CentralKotlinDBClass? = null

        fun getDatabaseObject(context: Context): CentralKotlinDBClass? {

            if (instance == null) {
                synchronized(CentralKotlinDBClass::class) {
                    instance = Room.databaseBuilder(context.applicationContext, CentralKotlinDBClass::class.java, dBName)
                            .build()
                }
            }
            return instance
        }

    }
}