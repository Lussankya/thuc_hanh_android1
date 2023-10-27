package com.mad.group8.th2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CatDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cat_db";
    private static final int DATABASE_VERSION = 1;

    // Define the table and columns
    public static final String TABLE_CATS = "cats";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMG_ID = "img_id";

    // SQL statement to create the table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CATS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_IMG_ID + " INTEGER" +
                    ");";

    public CatDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATS);
        onCreate(db);
    }
}

