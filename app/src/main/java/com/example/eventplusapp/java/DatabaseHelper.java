// File: app/src/main/java/com/example/eventplusapp/java/DatabaseHelper.java
package com.example.eventplusapp.java;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventplus.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DatabaseContract.UserEntry.TABLE_NAME + " (" +
                DatabaseContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.UserEntry.COLUMN_FIRST_NAME + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_LAST_NAME + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_EMAIL + " TEXT, " +
                DatabaseContract.UserEntry.COLUMN_PASSWORD + " TEXT);";

        String SQL_CREATE_EVENT_TABLE = "CREATE TABLE " + DatabaseContract.EventEntry.TABLE_NAME + " (" +
                DatabaseContract.EventEntry.COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.EventEntry.COLUMN_EVENT_NAME + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_DESCRIPTION + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_DATE + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_LOCATION + " TEXT, " +
                DatabaseContract.EventEntry.COLUMN_USER_ID + " INTEGER, " +
                "FOREIGN KEY(" + DatabaseContract.EventEntry.COLUMN_USER_ID + ") REFERENCES " +
                DatabaseContract.UserEntry.TABLE_NAME + "(" + DatabaseContract.UserEntry.COLUMN_USER_ID + "));";

        String SQL_CREATE_EVENT_USER_TABLE = "CREATE TABLE " + DatabaseContract.EventUserEntry.TABLE_NAME + " (" +
                DatabaseContract.EventUserEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " INTEGER, " +
                DatabaseContract.EventUserEntry.COLUMN_USER_ID + " INTEGER, " +
                DatabaseContract.EventUserEntry.COLUMN_STATUS + " TEXT DEFAULT 'hinzufügen', " +
                "FOREIGN KEY(" + DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + ") REFERENCES " +
                DatabaseContract.EventEntry.TABLE_NAME + "(" + DatabaseContract.EventEntry.COLUMN_EVENT_ID + "), " +
                "FOREIGN KEY(" + DatabaseContract.EventUserEntry.COLUMN_USER_ID + ") REFERENCES " +
                DatabaseContract.UserEntry.TABLE_NAME + "(" + DatabaseContract.UserEntry.COLUMN_USER_ID + "));";

        String SQL_CREATE_REMINDER_TABLE = "CREATE TABLE " + DatabaseContract.ReminderEntry.TABLE_NAME + " (" +
                DatabaseContract.ReminderEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.ReminderEntry.COLUMN_MESSAGE + " TEXT NOT NULL, " +
                DatabaseContract.ReminderEntry.COLUMN_RequestedUser_Id + " INTEGER NOT NULL, " +
                DatabaseContract.ReminderEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + ") REFERENCES " +
                DatabaseContract.EventEntry.TABLE_NAME + "(" + DatabaseContract.EventEntry._ID + "));";

        String SQL_CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + DatabaseContract.AppointmentEntry.TABLE_NAME + " (" +
                DatabaseContract.AppointmentEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.AppointmentEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                DatabaseContract.AppointmentEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                DatabaseContract.AppointmentEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + " INTEGER, " +
                "FOREIGN KEY(" + DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + ") REFERENCES " +
                DatabaseContract.EventEntry.TABLE_NAME + "(" + DatabaseContract.EventEntry.COLUMN_EVENT_ID + "));";

        db.execSQL(SQL_CREATE_REMINDER_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_EVENT_TABLE);
        db.execSQL(SQL_CREATE_EVENT_USER_TABLE);
        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.EventEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.EventUserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ReminderEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.AppointmentEntry.TABLE_NAME);
        onCreate(db);
    }
}