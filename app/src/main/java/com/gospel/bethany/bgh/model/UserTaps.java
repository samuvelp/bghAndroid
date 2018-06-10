package com.gospel.bethany.bgh.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class UserTaps implements Serializable {
    String key;
    long createdAt=0;

    public UserTaps() {
    }

    public UserTaps(String key, long createdAt) {
        this.key = key;
        this.createdAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}

