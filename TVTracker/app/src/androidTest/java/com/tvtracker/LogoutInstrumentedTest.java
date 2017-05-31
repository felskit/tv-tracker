package com.tvtracker;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.AppCompatImageButton;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class LogoutInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void Logout() {
        activityRule.getActivity();
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        ActivityMonitor monitor = instrumentation.addMonitor(LoginActivity.class.getName(), null, false);
        Activity activity = monitor.waitForActivityWithTimeout(500);
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.logout_button)).perform(click());
        assertNotNull(activity);
        assertEquals(activity.getClass(), LoginActivity.class);
    }
}
