package com.tvtracker.controllers;

public class ControllerConfig {
    private static final String baseApiUrl = "http://192.168.0.13/tvtracker/api/";
    public static int userId = -1;

    public String getBaseApiUrl() {
        return baseApiUrl;
    }
}
