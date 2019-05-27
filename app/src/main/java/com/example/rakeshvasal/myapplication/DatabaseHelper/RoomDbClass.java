package com.example.rakeshvasal.myapplication.DatabaseHelper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.rakeshvasal.myapplication.Dao.UserDao;

public abstract class RoomDbClass extends RoomDatabase {

    abstract UserDao userDao();

    private static RoomDbClass dbClass;

    public static RoomDbClass getRoomDbInstance(Context context) {
        if (dbClass == null) {
            dbClass = Room.databaseBuilder(context, RoomDbClass.class, "RoomDb").build();
        }
        return dbClass;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
