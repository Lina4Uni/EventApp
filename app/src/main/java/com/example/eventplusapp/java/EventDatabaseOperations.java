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

    public String getEventNameById(int eventId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String eventName = null;

        String[] projection = {
                DatabaseContract.EventEntry.COLUMN_EVENT_NAME
        };

        String selection = DatabaseContract.EventEntry.COLUMN_EVENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(eventId) };

        Cursor cursor = db.query(
                DatabaseContract.EventEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            eventName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME));
        }

        cursor.close();
        db.close();
        return eventName;
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
    public List<String> getEventsByUserId(int userId) {
        List<String> eventList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + DatabaseContract.EventEntry.COLUMN_EVENT_NAME +
                " FROM " + DatabaseContract.EventEntry.TABLE_NAME +
                " WHERE " + DatabaseContract.EventEntry.COLUMN_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                eventList.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventEntry.COLUMN_EVENT_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return eventList;
    }
    public List<Reminder> getRemindersForUser(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Reminder> reminders = new ArrayList<>();

        String[] projection = {
                DatabaseContract.ReminderEntry.COLUMN_ID,
                DatabaseContract.ReminderEntry.COLUMN_MESSAGE,
                DatabaseContract.ReminderEntry.COLUMN_TYPE,
                DatabaseContract.ReminderEntry.COLUMN_EVENT_ID
        };

        String selection = DatabaseContract.ReminderEntry.COLUMN_RequestedUser_Id + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(
                DatabaseContract.ReminderEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ReminderEntry.COLUMN_ID));
            String message = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ReminderEntry.COLUMN_MESSAGE));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ReminderEntry.COLUMN_TYPE));
            int eventId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ReminderEntry.COLUMN_EVENT_ID));

            reminders.add(new Reminder(id, message, type, eventId, userId));
        }

        cursor.close();
        db.close();
        return reminders;
    }

    public void insertReminder(String message, int requestedUserId, String type, int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ReminderEntry.COLUMN_MESSAGE, message);
        values.put(DatabaseContract.ReminderEntry.COLUMN_RequestedUser_Id, requestedUserId);
        values.put(DatabaseContract.ReminderEntry.COLUMN_TYPE, type);
        values.put(DatabaseContract.ReminderEntry.COLUMN_EVENT_ID, eventId);
        db.insert(DatabaseContract.ReminderEntry.TABLE_NAME, null, values);
    }

//    public void updateUserStatus(int userId, int eventId, Status newStatus) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DatabaseContract.EventUserEntry.COLUMN_STATUS, newStatus.toString());
//
//        String selection = DatabaseContract.EventUserEntry.COLUMN_USER_ID + " = ? AND " +
//                DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?";
//        String[] selectionArgs = { String.valueOf(userId), String.valueOf(eventId) };
//
//        // Check if the row exists
//        Cursor cursor = db.query(DatabaseContract.EventUserEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
//        if (cursor.getCount() > 0) {
//            // Row exists, update it
//            db.update(DatabaseContract.EventUserEntry.TABLE_NAME, values, selection, selectionArgs);
//        } else {
//            // Row does not exist, insert a new one
//            values.put(DatabaseContract.EventUserEntry.COLUMN_USER_ID, userId);
//            values.put(DatabaseContract.EventUserEntry.COLUMN_EVENT_ID, eventId);
//            values.put(DatabaseContract.EventUserEntry.COLUMN_STATUS, newStatus.toString());
//            db.insert(DatabaseContract.EventUserEntry.TABLE_NAME, null, values);
//        }
//        cursor.close();
//        db.close();
//    }
    public void updateUserStatus(int userId, int eventId, Status newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.EventUserEntry.COLUMN_STATUS, newStatus.toString());

        String selection = DatabaseContract.EventUserEntry.COLUMN_USER_ID + " = ? AND " +
                DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId), String.valueOf(eventId) };

        db.update(DatabaseContract.EventUserEntry.TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }

    public void deleteReminder(int userId, int eventId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = DatabaseContract.ReminderEntry.COLUMN_RequestedUser_Id + " = ? AND " +
                DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId), String.valueOf(eventId) };

        db.delete(DatabaseContract.ReminderEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }
    public List<Participant> getParticipantsForEvent(int eventId) {
        List<Participant> participants = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT u." + DatabaseContract.UserEntry.COLUMN_FIRST_NAME + ", " +
                "u." + DatabaseContract.UserEntry.COLUMN_LAST_NAME +
                " FROM " + DatabaseContract.UserEntry.TABLE_NAME + " u" +
                " JOIN " + DatabaseContract.EventUserEntry.TABLE_NAME + " eu" +
                " ON u." + DatabaseContract.UserEntry.COLUMN_USER_ID + " = eu." + DatabaseContract.EventUserEntry.COLUMN_USER_ID +
                " WHERE eu." + DatabaseContract.EventUserEntry.COLUMN_EVENT_ID + " = ? AND " +
                "eu." + DatabaseContract.EventUserEntry.COLUMN_STATUS + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(eventId), Status.ACCEPTED.toString()});

        if (cursor.moveToFirst()) {
            do {
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserEntry.COLUMN_LAST_NAME));
                participants.add(new Participant(firstName, lastName));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return participants;
    }

    public List<Appointment> getAppointmentsByEventName(String eventId) {
        List<Appointment> appointmentList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DatabaseContract.AppointmentEntry.TABLE_NAME +
                " WHERE " + DatabaseContract.ReminderEntry.COLUMN_EVENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{eventId});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.AppointmentEntry._ID));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppointmentEntry.COLUMN_DESCRIPTION));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppointmentEntry.COLUMN_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AppointmentEntry.COLUMN_TIME));
                int eventIdFromDb = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.ReminderEntry.COLUMN_EVENT_ID));
                appointmentList.add(new Appointment(id, date, time, description, eventIdFromDb));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return appointmentList;
    }

    // File: app/src/main/java/com/example/eventplusapp/java/EventDatabaseOperations.java
    public void insertAppointment(Appointment appointment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AppointmentEntry.COLUMN_DESCRIPTION, appointment.getDescription());
        values.put(DatabaseContract.AppointmentEntry.COLUMN_DATE, appointment.getDate());
        values.put(DatabaseContract.AppointmentEntry.COLUMN_TIME, appointment.getTime());
        values.put(DatabaseContract.ReminderEntry.COLUMN_EVENT_ID, appointment.getEventId());

        db.insert(DatabaseContract.AppointmentEntry.TABLE_NAME, null, values);
        db.close();
    }
}