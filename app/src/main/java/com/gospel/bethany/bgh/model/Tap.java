package com.gospel.bethany.bgh.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by samuvelp on 24/03/18.
 */
@IgnoreExtraProperties
public class Tap implements Serializable {
    public String key;
    public String message = "";
    public String type = "";
    public String userId = "";
    public long createdAt = 0;

    public Tap() {
    }

    public Tap(String key, String message, String type, String userId, long createdAt) {
        this.key = key;
        this.message = message;
        this.type = type;
        this.userId = userId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
