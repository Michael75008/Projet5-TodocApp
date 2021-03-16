package com.example.todocapp.ui;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.todocapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todocapp.utils.RecyclerViewMatcher.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTaskList_sortAction_sortTasksFromAtoZ() {
        clickOnFilterWithText(R.string.sort_alphabetical);

        checkRecyclerViewAtPosition(0, "A");
        checkRecyclerViewAtPosition(1, "B");
        checkRecyclerViewAtPosition(2, "C");
    }

    @Test
    public void myTaskList_sortAction_sortTasksFromZtoA() {
        clickOnFilterWithText(R.string.sort_alphabetical_invert);

        checkRecyclerViewAtPosition(0, "C");
        checkRecyclerViewAtPosition(1, "B");
        checkRecyclerViewAtPosition(2, "A");
    }

    @Test
    public void myTaskList_sortAction_sortTasksFromRecentToOlder() {
        clickOnFilterWithText(R.string.sort_recent_first);

        checkRecyclerViewAtPosition(0, "C");
        checkRecyclerViewAtPosition(1, "B");
        checkRecyclerViewAtPosition(2, "A");
    }


    @Test
    public void myTaskList_sortAction_sortTasksFromOlderToRecent() {
        clickOnFilterWithText(R.string.sort_oldest_first);

        checkRecyclerViewAtPosition(2, "C");
        checkRecyclerViewAtPosition(1, "B");
        checkRecyclerViewAtPosition(0, "A");
    }

    private void checkRecyclerViewAtPosition(int position, String text) {
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(position, R.id.lbl_task_name))
                .check(matches(withText(text)));
    }

    private void clickOnFilterWithText(int name) {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(name)).perform(click());
    }
}
