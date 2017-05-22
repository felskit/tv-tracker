package com.tvtracker;

import com.tvtracker.tools.DateConverter;

import org.junit.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jacek on 21.05.2017.
 */

public class DateConverterUnitTests {

    private int[] UTCOffset(Date date) {
        long time = date.getTime();
        int utcOffset = TimeZone.getTimeZone("UTC").getOffset(time);
        int defaultOffset = TimeZone.getDefault().getOffset(time);
        return new int[] {(defaultOffset - utcOffset) / (1000 * 60 * 60), ((defaultOffset - utcOffset) / (1000 * 60)) % 60 };
    }

    private boolean compareHour(String date, int hour, int minutes) {
        Date convertedDate = DateConverter.ConvertFromUTC(date);
        int[] offset = UTCOffset(convertedDate);
        int cHour = hour + offset[0];
        if (offset[1] + minutes >= 60) {
            cHour++;
        }
        if (offset[1] + minutes <= 0) {
            cHour--;
        }
        return cHour == DateConverter.getHour(convertedDate);
    }

    private <T> void assertIncludingOffset(Date date, T expectedTrue, T expectedFalse, T actual) {
        int[] offset = UTCOffset(date);
        if (offset[0] > 0 || offset[1] > 0) {
            assertEquals(expectedTrue, actual);
        }
        else {
            assertEquals(expectedFalse, actual);
        }
    }

    @Test
    public void getHour_isCorrect() {
        assertTrue(compareHour("2017-03-23T11:29:56", 11, 29));
        assertTrue(compareHour("2017-06-11T20:43:19", 20, 43));
    }

    @Test
    public void getDate_isCorrect() {
        Date convertedDate = DateConverter.ConvertFromUTC("2017-01-01T23:59:00");
        assertIncludingOffset(convertedDate, "02-01-2017", "01-01-2017", DateConverter.getDate(convertedDate));
    }

    @Test
    public void getMinutes_isCorrect() {
        Date convertedDate = DateConverter.ConvertFromUTC("2017-02-13T15:49:03");
        assertEquals(49 + UTCOffset(convertedDate)[1], DateConverter.getMinutes(convertedDate));
    }

    @Test
    public void getYear_isCorrect() {
        Date convertedDate = DateConverter.ConvertFromUTC("2016-12-31T23:59:00");
        assertIncludingOffset(convertedDate, 2017, 2016, DateConverter.getYear(convertedDate));
    }

    @Test
    public void getMonth_isCorrect() {
        Date convertedDate = DateConverter.ConvertFromUTC("2016-12-31T23:59:00");
        assertIncludingOffset(convertedDate, 1, 12, DateConverter.getMonth(convertedDate));
    }

    @Test
    public void getDay_isCorrect() {
        Date convertedDate = DateConverter.ConvertFromUTC("2016-12-31T23:59:00");
        assertIncludingOffset(convertedDate, 1, 31, DateConverter.getDay(convertedDate));
    }
}
