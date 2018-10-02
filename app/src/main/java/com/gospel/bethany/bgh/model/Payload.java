package com.gospel.bethany.bgh.model;

public class Payload {
    private String audioUrl = "";
    private int duration = 0;

    public Payload() {
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
