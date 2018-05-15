package com.example.mwojcik.wishplaces.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favyplaces.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_PLACES = "places";
    public static final String COLUMN_PLACE_ID = "_id";
    public static final String COLUMN_PLACE_NAME = "place_name";
    public static final String COLUMN_PLACE_SUMMARY = "place_summary";
    public static final String COLUMN_PLACE_DESCRIPTION = "place_description";
    public static final String COLUMN_PLACE_LATITUDE = "place_latitude";
    public static final String COLUMN_PLACE_LONGITUDE = "place_longitude";

    private static final String TABLE_PLACES_CREATE = "create table "
            + TABLE_PLACES + "("
            + COLUMN_PLACE_ID + " integer primary key autoincrement,"
            + COLUMN_PLACE_NAME + " text not null,"
            + COLUMN_PLACE_SUMMARY + " text not null,"
            + COLUMN_PLACE_DESCRIPTION + " text not null,"
            + COLUMN_PLACE_LATITUDE + " text not null,"
            + COLUMN_PLACE_LONGITUDE + " text not null"
            + ");";


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_PLACES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(sqLiteDatabase);
    }
}
