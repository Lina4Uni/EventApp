// File: app/src/main/java/com/example/eventplusapp/java/DatabaseContract.java
package com.example.eventplusapp.java;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_USER_ID = "User_Id";
        public static final String COLUMN_FIRST_NAME = "First_Name";
        public static final String COLUMN_LAST_NAME = "Last_Name";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_PASSWORD = "Password";
    }

    public static class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "Event";
        public static final String COLUMN_EVENT_ID = "Event_Id";
        public static final String COLUMN_EVENT_NAME = "Event_Name";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_LOCATION = "Location";
        public static final String COLUMN_USER_ID = "User_Id"; // Foreign key
    }

    public static class EventUserEntry implements BaseColumns {
        public static final String TABLE_NAME = "EventUser";
        public static final String COLUMN_ID = "EventUser_Id";
        public static final String COLUMN_EVENT_ID = "Event_Id"; // Foreign key
        public static final String COLUMN_USER_ID = "User_Id"; // Foreign key
        public static final String COLUMN_STATUS = "Status";
    }
}