// File: app/src/main/java/com/example/eventplusapp/java/EventDatabaseOperations.java
package com.example.eventplusapp.java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventDatabaseOperations {
    private DatabaseHelper dbHelper;

    public EventDatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insertEvent(Event event) {
        insertEvent(event.getEventName(), event.getDescription(), event.getDate(), event.getLocation());
    }
    public void updateEvent(Event event) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventEntry.COLUMN_EVENT_NAME, event.getEventName());
        values.put(DatabaseContract.EventEntry.COLUMN_DESCRIPTION, event.getDescription());
        values.put(DatabaseContract.EventEntry.COLUMN_DATE, event.getDate());
        values.put(DatabaseContract.EventEntry.COLUMN_LOCATION, event.getLocation());
        db.update(DatabaseContract.EventEntry.TABLE_NAME, values, DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(event.getEventId())});
        db.close();
    }

    public void insertEvent(String eventName, String description, String date, String location) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventEntry.COLUMN_EVENT_NAME, eventName);
        values.put(DatabaseContract.EventEntry.COLUMN_DESCRIPTION, description);
        values.put(DatabaseContract.EventEntry.COLUMN_DATE, date);
        values.put(DatabaseContract.EventEntry.COLUMN_LOCATION, location);
        db.insert(DatabaseContract.EventEntry.TABLE_NAME, null, values);
        db.close();
    }
    public void deleteEvent(int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseContract.EventEntry.TABLE_NAME, DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(eventId)});
        db.close();
    }
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
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
                Event event = new Event(eventId, eventName, description, date, location, new ArrayList<>());
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }
    public Event getEvent(int eventId) {
        Event event = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.EventEntry.TABLE_NAME + " WHERE " + DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId)});
        if (cursor.moveToFirst()) {
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DESCRIPTION));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_DATE));
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_LOCATION));
            event = new Event(0, eventName, description, date, location, new ArrayList<>());
        }
        cursor.close();
        db.close();
        return event;
    }
}