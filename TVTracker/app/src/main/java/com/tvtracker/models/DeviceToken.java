package com.tvtracker.models;


public class DeviceToken {
    public int userId;
    public String oldToken;
    public String newToken;

    public DeviceToken(int userId, String oldToken, String newToken) {
        this.userId = userId;
        this.oldToken = oldToken;
        this.newToken = newToken;
    }
}
