package com.tvtracker.models;

public class UserId {
    public String fbId;
    public String googleId;
    public String token;

    public UserId(String fbId, String googleId, String token) {
        this.fbId = fbId;
        this.googleId = googleId;
        this.token = token;
    }
}
