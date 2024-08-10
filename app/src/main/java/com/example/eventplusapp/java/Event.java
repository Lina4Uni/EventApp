// File: app/src/main/java/com/example/eventplusapp/java/Event.java
package com.example.eventplusapp.java;

import android.widget.ArrayAdapter;

import java.util.List;

public class Event {
    private int eventId;
    private String eventName;
    private String description;
    private String date;
    private String location;
    private List<User> participants;

    public Event(int eventId, String eventName, String description, String date, String location, List<User> participants) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.location = location;
        this.participants = participants;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
    @Override
    public String toString() {
        return eventName;
    }

}