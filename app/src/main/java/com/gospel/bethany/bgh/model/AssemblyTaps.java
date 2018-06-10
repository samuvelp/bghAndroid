package com.gospel.bethany.bgh.model;

import java.io.Serializable;

/**
 * Created by samuvelp on 30/03/18.
 */

public class AssemblyTaps implements Serializable {
    String key;
    String userId = "";

    public AssemblyTaps() {

    }

    public AssemblyTaps(String key, String userId) {
        this.key = key;
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
