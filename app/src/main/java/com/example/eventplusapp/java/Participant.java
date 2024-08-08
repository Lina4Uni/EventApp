package com.example.eventplusapp.java;

public class Participant {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String event;

    public Participant(String name, String email, String phone, String address, String event) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.event = event;
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
