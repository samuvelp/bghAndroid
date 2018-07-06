package com.gospel.bethany.bgh.model;

public class CalendarEvents {
    String key = "";
    String title = "";
    String description = "";
    String location = "";
    long timestamp = 0;

    public CalendarEvents() {
    }

    public CalendarEvents(String key, String title, String description, String location, long timestamp) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.location = location;
        this.timestamp = timestamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
