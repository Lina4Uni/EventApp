// File: app/src/main/java/com/example/eventplusapp/java/User.java
package com.example.eventplusapp.user;

public class User {

    private int userId;
    private String vorname;
    private String nachname;
    private String mail;
    private String passwort;

    public User(int userId, String vorname, String nachname, String mail, String passwort) {
        this.userId = userId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.mail = mail;
        this.passwort = passwort;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
}