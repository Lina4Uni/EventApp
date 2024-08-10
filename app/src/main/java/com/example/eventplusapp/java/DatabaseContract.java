// File: app/src/main/java/com/example/eventplusapp/java/DatabaseContract.java
package com.example.eventplusapp.java;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_USER_ID = "User_Id";
        public static final String COLUMN_FIRST_NAME = "Vorname";
        public static final String COLUMN_LAST_NAME = "Nachname";
        public static final String COLUMN_EMAIL = "Mail";
        public static final String COLUMN_PASSWORD = "Passwort";
    }

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Event";
        public static final String COLUMN_EVENT_ID = "Event_Id";
        public static final String COLUMN_EVENT_NAME = "EventName";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_LOCATION = "Location";
    }

    public static class UserEventEntry implements BaseColumns {
        public static final String TABLE_NAME = "UserEvent";
        public static final String COLUMN_USER_EVENT_USER_ID = "User_Id";
        public static final String COLUMN_USER_EVENT_EVENT_ID = "Event_Id";
    }
}