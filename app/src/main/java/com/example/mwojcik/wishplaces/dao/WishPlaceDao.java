package com.example.mwojcik.wishplaces.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mwojcik.wishplaces.dto.WishPlace;

import java.util.ArrayList;
import java.util.List;

public class WishPlaceDao {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public WishPlaceDao(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Tworzymy nowy obiekt WishPlace w bazie danych
    */
    public void createWishPlace(String name, String summary, String description, String latitude, String longitude){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_PLACE_NAME, name);
        contentValues.put(DatabaseHelper.COLUMN_PLACE_SUMMARY, summary);
        contentValues.put(DatabaseHelper.COLUMN_PLACE_DESCRIPTION, description);
        contentValues.put(DatabaseHelper.COLUMN_PLACE_LATITUDE, latitude);
        contentValues.put(DatabaseHelper.COLUMN_PLACE_LONGITUDE, longitude);

        database.insert(DatabaseHelper.TABLE_PLACES, null, contentValues);
    }
    /**
     * Usuwamy dany obiekt WishPlace z bazy
     */
    public void removeWishPlace(WishPlace place){
        database.delete(DatabaseHelper.TABLE_PLACES, "_id=?", new String[]{String.valueOf(place.getId())});
    }

    //kursor 'skaczacy' po kolumnach
    private WishPlace cursorToLocation(Cursor cursor){
        WishPlace place = new WishPlace();
        place.setId(cursor.getInt(0));
        place.setName(cursor.getString(1));
        place.setSummary(cursor.getString(2));
        place.setDescription(cursor.getString(3));
        place.setLatitude(cursor.getString(4));
        place.setLongitude(cursor.getString(5));

        return place;
    }

    /**
     * Pobieramy obiekt WishPlace z bazy na podstawie jego id
     */
    public WishPlace getFavyPlaceById(int id){
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PLACES + " WHERE "
                + DatabaseHelper.COLUMN_PLACE_ID + "=?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        return cursorToLocation(cursor);
    }

    /**
     * Pobieramy wszystkie obikety WishPLace i zwracamy liste tych obiektow
     */
    public List<WishPlace> getAllFavyPlaces(){
        List<WishPlace> resultList = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PLACES, new String[]{});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            resultList.add(cursorToLocation(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return resultList;
    }
}
