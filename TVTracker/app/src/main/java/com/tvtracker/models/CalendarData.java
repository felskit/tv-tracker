package com.tvtracker.models;

/**
 * Created by Jacek on 20.05.2017.
 */

public class CalendarData {
    public int userId;
    public int year;
    public int month;

    public CalendarData(int userId, int month, int year) {
        this.userId = userId;
        this.month = month;
        this.year = year;
    }
}
