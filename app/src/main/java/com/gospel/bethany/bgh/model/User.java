package com.gospel.bethany.bgh.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by samuvelp on 24/03/18.
 */

public class User implements Serializable {
    public long createdAt;
    public String displayName = "";
    public String photoUrl = "";
    public String key;
    public String assemblyId = "";
    public boolean allowCreation = false;

    public User() {
    }


    public User(long createdAt, String displayName, String photoUrl, String key, String assemblyId, boolean allowCreation) {
        this.createdAt = createdAt;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.key = key;
        this.assemblyId = assemblyId;
        this.allowCreation = allowCreation;
    }

    public String getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(String assemblyId) {
        this.assemblyId = assemblyId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isAllowCreation() {
        return allowCreation;
    }

    public void setAllowCreation(boolean allowCreation) {
        this.allowCreation = allowCreation;
    }
}
