package com.example.rakeshvasal.myapplication.DatabaseHelper;




import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

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
