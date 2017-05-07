package com.tvtracker.controllers;

public class ControllerConfig {
    private static final String baseApiUrl = "http://192.168.1.2/tvtracker/api/";
    public static int userId;

    public String getBaseApiUrl() {
        return baseApiUrl;
    }
}
