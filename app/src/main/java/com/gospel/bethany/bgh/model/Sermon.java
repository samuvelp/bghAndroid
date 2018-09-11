package com.gospel.bethany.bgh.model;

import java.io.Serializable;

public class Sermon implements Serializable {
    public String key;
    public String author = "";
    public String title = "";
    public long createAt = 0;

    public Sermon() {
    }

    public Sermon(String key, String author, String title, long createAt) {
        this.key = key;
        this.author = author;
        this.title = title;
        this.createAt = createAt;
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

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public class Payload {
        public String audioUrl = "";

        public String getAudioUrl() {
            return audioUrl;
        }

        public void setAudioUrl(String audioUrl) {
            this.audioUrl = audioUrl;
        }
    }
}