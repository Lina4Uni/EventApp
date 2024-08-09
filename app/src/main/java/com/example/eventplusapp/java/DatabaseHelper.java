// File: app/src/main/java/com/example/eventplusapp/java/DatabaseHelper.java
package com.example.eventplusapp.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "eventplus.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "User_Id";
    private static final String COLUMN_FIRST_NAME = "Vorname";
    private static final String COLUMN_LAST_NAME = "Nachname";
    private static final String COLUMN_EMAIL = "Mail";
    private static final String COLUMN_PASSWORD = "Passwort";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void insertUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String email, String password) {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{email, password});
            boolean userExists = cursor.moveToFirst();
            cursor.close();
            db.close();
            return userExists;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;

    }
}