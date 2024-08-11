// File: app/src/main/java/com/example/eventplusapp/java/EventDatabaseOperations.java
package com.example.eventplusapp.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventDatabaseOperations {
    private DatabaseHelper dbHelper;

    public EventDatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
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

    public void insertEvent(User currUser, Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventEntry.COLUMN_EVENT_NAME, event.getEventName());
        values.put(DatabaseContract.EventEntry.COLUMN_DESCRIPTION, event.getDescription());
        values.put(DatabaseContract.EventEntry.COLUMN_DATE, event.getDate());
        values.put(DatabaseContract.EventEntry.COLUMN_LOCATION, event.getLocation());
        values.put(DatabaseContract.EventEntry.COLUMN_USER_ID, event.getUserId());

        // Insert the event and get the new Event_ID
        long eventId = db.insert(DatabaseContract.EventEntry.TABLE_NAME, null, values);

        // Insert the user as a participant in the EventUserEntry table
        ContentValues eventUserValues = new ContentValues();
        eventUserValues.put(DatabaseContract.EventUserEntry.COLUMN_EVENT_ID, eventId);
        eventUserValues.put(DatabaseContract.EventUserEntry.COLUMN_USER_ID, currUser.getUserId());
        eventUserValues.put(DatabaseContract.EventUserEntry.COLUMN_STATUS, Status.ACCEPTED.toString());

        db.insert(DatabaseContract.EventUserEntry.TABLE_NAME, null, eventUserValues);
        db.close();
    }

    public void updateEvent(Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventEntry.COLUMN_EVENT_NAME, event.getEventName());
        values.put(DatabaseContract.EventEntry.COLUMN_DESCRIPTION, event.getDescription());
        values.put(DatabaseContract.EventEntry.COLUMN_DATE, event.getDate());
        values.put(DatabaseContract.EventEntry.COLUMN_LOCATION, event.getLocation());
        values.put(DatabaseContract.EventEntry.COLUMN_USER_ID, event.getUserId());
        db.update(DatabaseContract.EventEntry.TABLE_NAME, values, DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(event.getEventId())});
        db.close();
    }
    // File: app/src/main/java/com/example/eventplusapp/java/EventDatabaseOperations.java
    public void deleteEvent(int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.EventEntry.TABLE_NAME, DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(eventId)});
        db.close();
    }
    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.EventEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int eventId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_ID));
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_LOCATION));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_USER_ID));

                Event event = new Event(eventId, eventName, description, date, location, new ArrayList<>(), userId);
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }

    public List<String> getEventsForUser(User loggedUser) {
        List<String> userEvents = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.EventEntry.COLUMN_EVENT_NAME +
                " FROM " + DatabaseContract.EventEntry.TABLE_NAME +
                " WHERE " + DatabaseContract.EventEntry.COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(loggedUser.getUserId())});

        if (cursor.moveToFirst()) {
            do {
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME));
                userEvents.add(eventName);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userEvents;
    }

    public int getEventIdByName(String eventName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT " + DatabaseContract.EventEntry.COLUMN_EVENT_ID +
                " FROM " + DatabaseContract.EventEntry.TABLE_NAME +
                " WHERE " + DatabaseContract.EventEntry.COLUMN_EVENT_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{eventName});
        int eventId = -1;
        if (cursor.moveToFirst()) {
            eventId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_ID));
        }
        cursor.close();
        db.close();
        return eventId;
    }
    public List<UserStatus> getUsersAndStatusesForEvent(int eventId) {
        List<UserStatus> userStatusList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT u." + DatabaseContract.UserEntry.COLUMN_USER_ID + ", " +
                "u." + DatabaseContract.UserEntry.COLUMN_EMAIL + ", " +
                "IFNULL(eu." + DatabaseContract.EventUserEntry.COLUMN_STATUS + ", 'ADD') AS status" +
                " FROM " + DatabaseContract.UserEntry.TABLE_NAME + " u" +
                " LEFT JOIN " + DatabaseContract.EventUserEntry.TABLE_NAME + " eu" +
                " ON u." + DatabaseContract.UserEntry.COLUMN_USER_ID + " = eu." + DatabaseContract.EventUserEntry.COLUMN_USER_ID +
                " AND eu." + DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?" +
                " ORDER BY u." + DatabaseContract.UserEntry.COLUMN_EMAIL;

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_USER_ID));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_EMAIL));
                String status = cursor.getString(cursor.getColumnIndexOrThrow("status"));
                userStatusList.add(new UserStatus(email, status, userId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userStatusList;
    }

    // File: app/src/main/java/com/example/eventplusapp/java/EventDatabaseOperations.java
    public List<Event> getEventsByUserId(int userId) {
        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.EventEntry.TABLE_NAME +
                " WHERE " + DatabaseContract.EventEntry.COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int eventId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_ID));
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DATE));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_LOCATION));
                Event event = new Event(eventId, eventName, description, date, location, new ArrayList<>(), userId);
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return eventList;
    }


    public void updateUserStatus(int userId, int eventId, Status newStatus) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventUserEntry.COLUMN_STATUS, newStatus.toString());

        String selection = DatabaseContract.EventUserEntry.COLUMN_USER_ID + " = ? AND " +
                DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId), String.valueOf(eventId) };

        db.update(DatabaseContract.EventUserEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }
}