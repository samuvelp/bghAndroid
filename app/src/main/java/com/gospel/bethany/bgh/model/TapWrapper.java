package com.gospel.bethany.bgh.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by samuvelp on 30/03/18.
 */
@IgnoreExtraProperties
public class TapWrapper implements Serializable{
   public List<Tap> tapList;
   public List<User> userList;

    public TapWrapper() {
    }

    public TapWrapper(List<Tap> tapList, List<User> userList) {
        this.tapList = tapList;
        this.userList = userList;
    }

    public List<Tap> getTapList() {
        return tapList;
    }

    public void setTapList(List<Tap> tapList) {
        this.tapList = tapList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
