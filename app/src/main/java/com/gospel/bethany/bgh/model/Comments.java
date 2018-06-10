package com.gospel.bethany.bgh.model;

import java.io.Serializable;

/**
 * Created by samuvelp on 25/03/18.
 */

public class Comments implements Serializable {
    String key ;
    String comment = "";
    long createdAt = 0;
    String userId = "";

    public Comments() {
    }

    public Comments(String key, String comment, long createdAt, String userId) {
        this.key = key;
        this.comment = comment;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
