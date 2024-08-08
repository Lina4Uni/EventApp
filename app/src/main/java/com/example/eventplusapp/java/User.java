package com.example.eventplusapp.java;

public class User {

    int personId;
    String vorname;
    String nachname;
    String passwort;

    public User(int personId, String vorname, String nachname, String passwort) {
        this.personId = personId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.passwort = passwort;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

}
