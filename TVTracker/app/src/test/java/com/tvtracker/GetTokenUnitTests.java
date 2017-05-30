package com.tvtracker;

import android.content.SharedPreferences;

import com.tvtracker.tools.TVTrackerFirebaseInstanceIDService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Jacek on 30.05.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetTokenUnitTests {

    @Mock
    SharedPreferences preferences;

    @Test
    public void getToken_isCorrect() {
        String id = "testId";
        when(preferences.getString("registration_id", null))
                .thenReturn(id);

        String result = TVTrackerFirebaseInstanceIDService.getToken(preferences);
        assertEquals(id, result);
    }
}
