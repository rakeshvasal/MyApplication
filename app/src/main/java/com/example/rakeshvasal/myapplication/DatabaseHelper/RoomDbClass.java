package com.example.rakeshvasal.myapplication.DatabaseHelper;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;


import com.example.rakeshvasal.myapplication.Dao.UserDao;
import com.example.rakeshvasal.myapplication.GetterSetter.User;

@Database(entities = User.class, version = 1, exportSchema = false)
public abstract class RoomDbClass extends RoomDatabase {

    public abstract UserDao userDao();

    private static RoomDbClass dbClass;

    public synchronized static RoomDbClass getRoomDbInstance(Context context) {
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
