package com.example.rakeshvasal.myapplication.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rakeshvasal on 30-Dec-16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "MyDatabase.db";
    public static int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public static String IMAGE_NAME = "image_name";
    public static String IMAGE_PATH = "image_path";
    public static String IMAGE_LAT = "image_lat";
    public static String IMAGE_LONG = "image_long";
    public static String IMAGE_TABLE = "image_table";
    public static String IMAGE_ID = "image_id";

    public static String Create_Image_Table = " CREATE TABLE " + IMAGE_TABLE + " ( " +
            IMAGE_ID + " integer primary key autoincrement, " +
            IMAGE_NAME + " text not null, " +
            IMAGE_PATH + " text not null, " +
            IMAGE_LAT + " text, " +
            IMAGE_LONG + " text " + " ) ";

    public static String DROP_IMAGE_TABLE = "Drop Table IF EXISTS " + IMAGE_TABLE;

    public static String LOCATION_NAME = "location_name";
    public static String LOCATION_LAT = "location_lat";
    public static String LOCATION_LATLNG = "location_lat";
    public static String LOCATION_COUNTRY_NAME = "location_lat";
    public static String LOCATION_LONG = "location_long";
    public static String LOCATION_TABLE = "location_table";
    public static String LOCATION_ID = "location_id";

    public static String Create_Location_Table = " CREATE TABLE " + LOCATION_TABLE + " ( " +
            LOCATION_ID + " integer primary key autoincrement, " +
            LOCATION_NAME + " text not null, " +
            LOCATION_LATLNG + " text not null, " +
            LOCATION_COUNTRY_NAME + " text, " +
            LOCATION_LAT + " text, " +
            LOCATION_LONG + " text " + " ) ";

    public static String DROP_LOCATION_TABLE = "Drop Table IF EXISTS " + LOCATION_TABLE;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static String RESTAURANT_TABLE = "restaurant_table";
    public static String RESTAURANT_ID_PK = "_id";
    public static String RESTAURANT_ID = "restaurant_id";
    public static String RESTAURANT_LOCALITY = "locality";
    public static String RESTAURANT_ADDRESS = "address";
    public static String RESTAURANT_SITE_URL = "siteurl";
    public static String RESTAURANT_LATITUDE = "latitude";
    public static String RESTAURANT_LONGITUDE = "longitude";
    public static String RESTAURANT_NAME = "restaurant_name";
    public static String RESTAURANT_PINCODE ="pincode";
    public static String RESTAURANT_CITY ="city";
    public static String RESTAURANT_COUNTRY_CODE ="country_code";
    public static String RESTAURANT_THUMB_URL ="thumb_url";
    public static String RESTAURANT_FEATURE_URL ="feature_url";
    public static String RESTAURANT_CONTACT_NUMBERS ="contact_numbers";
    public static String RESTAURANT_AVERAGE_RATING ="average_rating";

    public static String Create_Restaurant_Table = " CREATE TABLE " + RESTAURANT_TABLE + " ( "
            + RESTAURANT_ID_PK + " integer primary key autoincrement, "
            + RESTAURANT_ID + " text, "
            + RESTAURANT_NAME + " text, "
            + RESTAURANT_LOCALITY + " text, "
            + RESTAURANT_ADDRESS + " text, "
            + RESTAURANT_SITE_URL + " text, "
            + RESTAURANT_LATITUDE + " text, "
            + RESTAURANT_PINCODE + " text, "
            + RESTAURANT_CITY + " text, "
            + RESTAURANT_COUNTRY_CODE + " text, "
            + RESTAURANT_THUMB_URL + " text, "
            + RESTAURANT_FEATURE_URL + " text, "
            + RESTAURANT_CONTACT_NUMBERS + " text, "
            + RESTAURANT_AVERAGE_RATING + " text, "
            + RESTAURANT_LONGITUDE + " text " + " ) ";

    public static String Drop_Restaurant_Table = " Drop Table IF EXISTS " + RESTAURANT_TABLE;


    public static String COMMENTS_TABLE = "comments_table";
    public static String COMMENTS_TEXT = "comments_text";
    public static String COMMENTS_PK = "_id";

    public static String Create_Comments_Table = " CREATE TABLE " + COMMENTS_TABLE + " ( "
            + COMMENTS_PK + " integer primary key autoincrement, "
            + RESTAURANT_ID + " text, "
            + COMMENTS_TEXT + " text " + " ) ";

    public static String Drop_Comments_Table = " Drop Table IF EXISTS " + COMMENTS_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Image_Table);
        db.execSQL(Create_Location_Table);
        db.execSQL(Create_Restaurant_Table);
        db.execSQL(Create_Comments_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_IMAGE_TABLE);
        db.execSQL(DROP_LOCATION_TABLE);
        db.execSQL(Drop_Restaurant_Table);
        db.execSQL(Drop_Comments_Table);
        onCreate(db);
    }
}
