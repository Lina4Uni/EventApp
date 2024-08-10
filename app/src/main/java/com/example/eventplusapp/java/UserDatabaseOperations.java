// File: app/src/main/java/com/example/eventplusapp/java/UserDatabaseOperations.java
package com.example.eventplusapp.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDatabaseOperations {
    private DatabaseHelper dbHelper;

    public UserDatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserEntry.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseContract.UserEntry.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseContract.UserEntry.COLUMN_EMAIL, email);
        values.put(DatabaseContract.UserEntry.COLUMN_PASSWORD, password);
        db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);
        db.close();
    }

    public User checkUser(String email, String password) {
        User user = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.UserEntry.TABLE_NAME + " WHERE " + DatabaseContract.UserEntry.COLUMN_EMAIL + " = ? AND " + DatabaseContract.UserEntry.COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        if (cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_USER_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_LAST_NAME));
            String mail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL));
            String passwort = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PASSWORD));
            user = new User(userId, firstName, lastName, mail, passwort);
        }
        cursor.close();
        db.close();
        return user;
    }
}