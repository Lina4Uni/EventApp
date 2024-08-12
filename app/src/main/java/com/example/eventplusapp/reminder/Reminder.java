// File: app/src/main/java/com/example/eventplusapp/java/Reminder.java
package com.example.eventplusapp.reminder;

public class Reminder {
    private int id;
    private String message;
    private String type;
    private int eventId;
    private int requestedUserId;

    public Reminder(int id, String message, String type, int eventId, int requestedUserId) {
        this.id = id;
        this.message = message;
        this.type = type;
        this.eventId = eventId;
        this.requestedUserId = requestedUserId;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public int getEventId() {
        return eventId;
    }

    public int getRequestedUserId() {
        return requestedUserId;
    }
}