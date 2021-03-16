package com.example.todocapp.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TasksOnUiInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTasksList_deleteTaskAction_shouldDisplayMessageOnlyWhenEmptyList() {
        onView(allOf(isDisplayed(), withId(R.id.list_tasks)))
                .check(matches(hasChildCount(3)));

        onClickDeleteButtonAtIndex(2);
        onClickDeleteButtonAtIndex(1);
        onClickDeleteButtonAtIndex(0);

        onView(allOf(withId(R.id.lbl_no_task), withText("Tu n’as aucune tâche à traiter"))).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void afterClass() {
    }

    private void onClickDeleteButtonAtIndex(int position) {
        onView(allOf(withId(R.id.img_delete), childAtPosition(
                childAtPosition(
                        withId(R.id.list_tasks),
                        position),
                1),
                isDisplayed())).perform(click());
    }
}
