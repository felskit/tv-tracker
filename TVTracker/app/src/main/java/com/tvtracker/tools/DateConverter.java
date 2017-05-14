package com.tvtracker.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jacek on 14.05.2017.
 */

public class DateConverter {

    public static Date ConvertToUTC(String timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        try {
            value = formatter.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getDate(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return dt;
    }

    public static String getTime(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return dt;
    }

    public static int getHour(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return Integer.parseInt(dt);
    }

    public static int getMinutes(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("mm");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return Integer.parseInt(dt);
    }

    public static int getYear(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return Integer.parseInt(dt);
    }

    public static int getMonth(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return Integer.parseInt(dt);
    }

    public static int getDay(Date datetime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(datetime);
        return Integer.parseInt(dt);
    }

}
