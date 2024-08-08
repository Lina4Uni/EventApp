package com.example.eventplusapp.java;


public class Event {
    private String eventName;
    private String description;
    private String date;
    private String location;
    private String participants;
    public Event(String name, String description, String date) {
        this.eventName = name;
        this.description = description;
        this.date = date;
    }

    // Getter und Setter
    public String getName() {
        return eventName;
    }

    public void setName(String name) {
        this.eventName = name;
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
}
