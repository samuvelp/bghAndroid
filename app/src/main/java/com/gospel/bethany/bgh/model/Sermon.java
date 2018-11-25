package com.gospel.bethany.bgh.model;

import java.io.Serializable;

public class Sermon implements Serializable {
    public String key;
    public String author = "";
    public String title = "";
    public long createdAt = 0;
    public String length = "00:00";
    public Payload payload;

    public Sermon() {
    }

    public Sermon(String key, String author, String title, long createdAt, String length) {
        this.key = key;
        this.author = author;
        this.title = title;
        this.createdAt = createdAt;
        this.length = length;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createAt) {
        this.createdAt = createdAt;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}
