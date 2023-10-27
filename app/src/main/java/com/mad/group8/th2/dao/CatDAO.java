package com.mad.group8.th2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mad.group8.th2.CatDbHelper;
import com.mad.group8.th2.model.Cat;

import java.util.ArrayList;
import java.util.List;

public class CatDAO {
    private SQLiteDatabase database;
    private CatDbHelper dbHelper;

    public CatDAO(Context context) {
        dbHelper = new CatDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Create a new cat record
    public long createCat(Cat cat) {
        ContentValues values = new ContentValues();
        values.put(CatDbHelper.COLUMN_NAME, cat.getName());
        values.put(CatDbHelper.COLUMN_PRICE, cat.getPrice());
        values.put(CatDbHelper.COLUMN_DESCRIPTION, cat.getDescription());
        values.put(CatDbHelper.COLUMN_IMG_ID, cat.getImgId());

        return database.insert(CatDbHelper.TABLE_CATS, null, values);
    }

    // Read a cat record by ID
    public Cat getCatById(int catId) {
        Cursor cursor = database.query(
                CatDbHelper.TABLE_CATS,
                null,
                CatDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(catId)},
                null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            Cat cat = cursorToCat(cursor);
            cursor.close();
            return cat;
        } else {
            return null;
        }
    }

    // Update a cat record
    public int updateCat(Cat cat) {
        ContentValues values = new ContentValues();
        values.put(CatDbHelper.COLUMN_NAME, cat.getName());
        values.put(CatDbHelper.COLUMN_PRICE, cat.getPrice());
        values.put(CatDbHelper.COLUMN_DESCRIPTION, cat.getDescription());
        values.put(CatDbHelper.COLUMN_IMG_ID, cat.getImgId());

        return database.update(
                CatDbHelper.TABLE_CATS,
                values,
                CatDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cat.getId())}
        );
    }

    // Delete a cat record
    public void deleteCat(int catId) {
        database.delete(
                CatDbHelper.TABLE_CATS,
                CatDbHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(catId)}
        );
    }

    // Get a list of all cat records
    public List<Cat> getAllCats() {
        List<Cat> cats = new ArrayList<>();
        Cursor cursor = database.query(
                CatDbHelper.TABLE_CATS,
                null, null, null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Cat cat = cursorToCat(cursor);
                cats.add(cat);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return cats;
    }

    // Helper method to convert a cursor to a CatModel
    private Cat cursorToCat(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(CatDbHelper.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(CatDbHelper.COLUMN_NAME));
        double price = cursor.getDouble(cursor.getColumnIndex(CatDbHelper.COLUMN_PRICE));
        String description = cursor.getString(cursor.getColumnIndex(CatDbHelper.COLUMN_DESCRIPTION));
        int imgId = cursor.getInt(cursor.getColumnIndex(CatDbHelper.COLUMN_IMG_ID));

        return new Cat(id, name, price, description, imgId);
    }
}
