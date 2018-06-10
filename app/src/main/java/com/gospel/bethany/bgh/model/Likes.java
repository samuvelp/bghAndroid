package com.gospel.bethany.bgh.model;

/**
 * Created by samuvelp on 25/03/18.
 */

public class Likes {
    String key ;
    long createdAt = 0;

    public Likes() {
    }

    public Likes(String key, long createdAt) {
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
