// File: app/src/main/java/com/example/eventplusapp/java/Appointment.java
package com.example.eventplusapp.appointment;

public class Appointment {
    private int id;
    private String date;
    private String time;
    private String description;
    private int eventId;

    public Appointment(int id, String date, String time, String description, int eventId)
    {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
        this.eventId = eventId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}