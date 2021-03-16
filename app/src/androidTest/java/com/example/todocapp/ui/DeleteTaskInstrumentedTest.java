package com.example.todocapp.ui;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.R;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todocapp.utils.RecyclerViewMatcher.childAtPosition;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class DeleteTaskInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTasksList_deleteTaskAction_shouldDeleteTaskOnUi() {
        //Check if we found our task to delete
        ViewInteraction textView1 = onView(
                allOf(withText("C"),
                        isDisplayed()));
        textView1.check(matches(isDisplayed()));
        // Delete action on our new task
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        2),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        //Check if the 3 tasks from db are displayed on screen
        onView(allOf(isDisplayed(), ViewMatchers.withId(R.id.list_tasks)))
                .check(matches(hasChildCount(2)));
    }

    @AfterClass
    public static void afterClass() {
    }
}
