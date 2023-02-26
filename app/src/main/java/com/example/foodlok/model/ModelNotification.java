package com.example.foodlok.model;

public class ModelNotification {
    int profile;
    String notification, time;

    public ModelNotification(int profile, String notification, String time) {
        this.profile = profile;
        this.notification = notification;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}