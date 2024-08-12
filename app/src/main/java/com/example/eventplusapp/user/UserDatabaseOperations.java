// File: app/src/main/java/com/example/eventplusapp/java/UserDatabaseOperations.java
package com.example.eventplusapp.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.eventplusapp.db.DatabaseContract;
import com.example.eventplusapp.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseOperations {
    private DatabaseHelper dbHelper;

    public UserDatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }



    public User insertUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.UserEntry.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseContract.UserEntry.COLUMN_LAST_NAME, lastName);
        values.put(DatabaseContract.UserEntry.COLUMN_EMAIL, email);
        values.put(DatabaseContract.UserEntry.COLUMN_PASSWORD, password);

        long rowId = db.insert(DatabaseContract.UserEntry.TABLE_NAME, null, values);

        User user = null;
        if (rowId != -1) {
            String query = "SELECT * FROM " + DatabaseContract.UserEntry.TABLE_NAME + " WHERE " + DatabaseContract.UserEntry.COLUMN_USER_ID + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(rowId)});
            if (cursor.moveToFirst()) {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_USER_ID));
                String firstNameDb = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_FIRST_NAME));
                String lastNameDb = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_LAST_NAME));
                String emailDb = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL));
                String passwordDb = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_PASSWORD));
                user = new User(userId, firstNameDb, lastNameDb, emailDb, passwordDb);
            }
            cursor.close();
        }
        db.close();
        return user;
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

    public List<UserStatus> getUserStatusByEventId(int eventId) {
        List<UserStatus> userStatusList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT u." + DatabaseContract.UserEntry.COLUMN_USER_ID + ", " +
                "u." + DatabaseContract.UserEntry.COLUMN_EMAIL + ", " +
                "eu." + DatabaseContract.EventUserEntry.COLUMN_STATUS +
                " FROM " + DatabaseContract.UserEntry.TABLE_NAME + " u" +
                " JOIN " + DatabaseContract.EventUserEntry.TABLE_NAME + " eu" +
                " ON u." + DatabaseContract.UserEntry.COLUMN_USER_ID + " = eu." + DatabaseContract.EventUserEntry.COLUMN_USER_ID +
                " WHERE eu." + DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_USER_ID));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventUserEntry.COLUMN_STATUS));
                userStatusList.add(new UserStatus(email, status, userId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userStatusList;
    }

//    public List<UserStatus> getUserStatusByEventId(int eventId) {
//        List<UserStatus> userStatusList = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = "SELECT u." + DatabaseContract.UserEntry.COLUMN_EMAIL + ", eu." + DatabaseContract.EventUserEntry.COLUMN_STATUS +
//                " FROM " + DatabaseContract.UserEntry.TABLE_NAME + " u" +
//                " JOIN " + DatabaseContract.EventUserEntry.TABLE_NAME + " eu" +
//                " ON u." + DatabaseContract.UserEntry.COLUMN_USER_ID + " = eu." + DatabaseContract.EventUserEntry.COLUMN_USER_ID +
//                " WHERE eu." + DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});
//
//        if (cursor.moveToFirst()) {
//            do {
//                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL));
//                String status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventUserEntry.COLUMN_STATUS));
//                userStatusList.add(new UserStatus(email, status));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return userStatusList;
//    }
}