package com.example.frontend;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

import android.os.Bundle;
import android.widget.EditText;

import androidx.preference.EditTextPreference;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class PreferenceTest {
    @Rule
    public ActivityScenarioRule<PreferenceActivity> activityRule =
            new ActivityScenarioRule<>(PreferenceActivity.class);



    @Test
    public void checkPreferenceIsDisplayed() {
//        onView(withId(R.id.list))
//                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Alarms"))))
//                .check(matches(isDisplayed()));
        onView(withText("Notifications")).check(matches(isDisplayed()));
        //check alarms
        onView(withText("Alarms")).check(matches(isDisplayed()));
        onView(withText("Enable morning alarm")).check(matches(isDisplayed()));
        onView(withText("Enable alarms for event")).check(matches(isDisplayed()));
        //check alerts
        onView(withText("Alerts")).check(matches(isDisplayed()));
        onView(withText("Traffic alerts")).check(matches(isDisplayed()));
        onView(withText("Weather alerts")).check(matches(isDisplayed()));
        onView(withText("Vibration alert")).check(matches(isDisplayed()));
        //check location
        onView(withText("Location")).check(matches(isDisplayed()));
        onView(withText("Home location")).check(matches(isDisplayed()));
        onView(withText("School location")).check(matches(isDisplayed()));
        onView(withText("Work location")).check(matches(isDisplayed()));

        //other preferences
        onView(withId(androidx.preference.R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Others"))))
                .check(matches(isDisplayed()));
        onView(withId(androidx.preference.R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Preparation time (min)"))))
                .check(matches(isDisplayed()));
        onView(withId(androidx.preference.R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Commute method"))))
                .check(matches(isDisplayed()));
        onView(withId(androidx.preference.R.id.recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Max missed bus"))))
                .check(matches(isDisplayed()));
//        onView(withText("Snooze duration (min)"))
//                .check(matches(isDisplayed()));

    }

    @Test
    public void inputErrorAddress() {
        // open the location dialog
        onView(withText("Home location")).perform(click());

        onView(withText("Your home location")).check(matches(isDisplayed()));

        //enter error address
        onView(withId(android.R.id.edit))
                .perform(typeText("123"), closeSoftKeyboard());

        //click ok button
        onView(withText("OK")).perform(click());
    }


    @Test
    public void checkPreferenceChange() {
        //enable notify
        onView(withText("Notifications")).perform(click());

        //change home location
        onView(withText("Home location")).perform(click());
        onView(withText("Your home location")).check(matches(isDisplayed()));
        onView(withId(android.R.id.edit))
                .perform(typeText("6138 Student Union Blvd, Vancouver"), closeSoftKeyboard());
        //click ok button
        onView(withText("OK")).perform(click());
    }
    @Test
    public void checkSave() {
        onView(withText("save")).perform(click());
    }

}
