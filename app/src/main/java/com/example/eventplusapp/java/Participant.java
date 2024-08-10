// File: app/src/main/java/com/example/eventplusapp/java/Participant.java
package com.example.eventplusapp.java;

public class Participant {
    private String lastName;
    private String email;

    public Participant(String lastName, String email) {
        this.lastName = lastName;
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}