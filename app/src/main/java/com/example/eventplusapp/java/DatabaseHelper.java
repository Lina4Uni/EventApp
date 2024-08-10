// File: app/src/main/java/com/example/eventplusapp/java/DatabaseHelper.java
package com.example.eventplusapp.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventplus.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "onCreate called");

        String createUserTable = "CREATE TABLE " + DatabaseContract.UserEntry.TABLE_NAME + " (" +
                DatabaseContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.UserEntry.COLUMN_FIRST_NAME + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_LAST_NAME + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_EMAIL + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_PASSWORD + " TEXT)";

        String createEventTable = "CREATE TABLE " + DatabaseContract.EventEntry.TABLE_NAME + " (" +
                DatabaseContract.EventEntry.COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.EventEntry.COLUMN_EVENT_NAME + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_DESCRIPTION + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_DATE + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_LOCATION + " TEXT)";

        String createUserEventTable = "CREATE TABLE " + DatabaseContract.UserEventEntry.TABLE_NAME + " (" +
                DatabaseContract.UserEventEntry.COLUMN_User_Event_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.UserEventEntry.COLUMN_USER_EVENT_USER_ID + " INTEGER, " +
                DatabaseContract.UserEventEntry.COLUMN_USER_EVENT_EVENT_ID + " INTEGER, " +
                "FOREIGN KEY(" + DatabaseContract.UserEventEntry.COLUMN_USER_EVENT_USER_ID + ") REFERENCES " + DatabaseContract.UserEntry.TABLE_NAME + "(" + DatabaseContract.UserEntry.COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + DatabaseContract.UserEventEntry.COLUMN_USER_EVENT_EVENT_ID + ") REFERENCES " + DatabaseContract.EventEntry.TABLE_NAME + "(" + DatabaseContract.EventEntry.COLUMN_EVENT_ID + "))";

        db.execSQL(createUserTable);
        db.execSQL(createEventTable);
        db.execSQL(createUserEventTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "onUpgrade called");
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.EventEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEventEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "onDowngrade called");
        onUpgrade(db, oldVersion, newVersion);
    }
}