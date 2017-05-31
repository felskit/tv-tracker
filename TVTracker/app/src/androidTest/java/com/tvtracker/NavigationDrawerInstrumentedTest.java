package com.tvtracker;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckedTextView;
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
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class NavigationDrawerInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void NavigateToHome() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(allOf(withText(R.string.fragment_home), withClassName(containsString(AppCompatCheckedTextView.class.getName())))).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), HomeFragment.class);
    }

    @Test
    public void NavigateToCalendar() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_calendar)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), CalendarFragment.class);
    }

    @Test
    public void NavigateToFavourites() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_favourites)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), FavouritesFragment.class);
    }

    @Test
    public void NavigateToSuggested() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_suggested)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), FavouritesFragment.class);
    }

    @Test
    public void NavigateToSearch() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_search)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), SearchFragment.class);
    }

    @Test
    public void NavigateToPreferences() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_preferences)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), PreferenceFragment.class);
    }

    @Test
    public void NavigateToAbout() {
        MainActivity activity = activityRule.getActivity();
        onView(withClassName(containsString(AppCompatImageButton.class.getName()))).perform(click());
        onView(withText(R.string.fragment_about)).perform(click());
        Fragment fragment = activity.getSupportFragmentManager().findFragmentById(R.id.content_main);
        assertNotNull(fragment);
        assertEquals(fragment.getClass(), AboutFragment.class);
    }
}
