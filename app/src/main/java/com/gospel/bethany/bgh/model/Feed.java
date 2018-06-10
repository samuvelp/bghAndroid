package com.gospel.bethany.bgh.model;

import java.io.Serializable;

/**
 * Created by samuvelp on 30/03/18.
 */

public class Feed implements Serializable {
    public Tap tap;
    public User user;

    public Feed() {
    }

    public Feed(Tap tap, User user) {
        this.tap = tap;
        this.user = user;
    }

    public Tap getTap() {
        return tap;
    }

    public void setTap(Tap tap) {
        this.tap = tap;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
