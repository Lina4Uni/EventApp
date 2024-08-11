// File: app/src/main/java/com/example/eventplusapp/java/UserStatus.java
package com.example.eventplusapp.java;

public class UserStatus {
    private String email;
    private String status;
    private int userId;

    public UserStatus(String email, String status, int userId) {
        this.email = email;
        this.status = status;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public int getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}