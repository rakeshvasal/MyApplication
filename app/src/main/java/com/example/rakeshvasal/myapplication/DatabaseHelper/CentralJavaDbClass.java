package com.example.rakeshvasal.myapplication.DatabaseHelper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rakeshvasal.myapplication.DbUser;

@Database(entities = { DbUser.class }, version = 1)
public abstract class CentralJavaDbClass extends RoomDatabase {

    private static CentralJavaDbClass db;
    private static final String DB_NAME = "javaDb";
    static synchronized CentralJavaDbClass getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    CentralJavaDbClass.class, DB_NAME).build();
        }
        return db;
    }
}
