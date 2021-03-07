package com.example.todocapp.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.todocapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todocapp.utils.TestUtils.withRecyclerView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class OptionsMenuInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTaskList_sortAction_sortTasksAlphabetical() {
        // Check if we find our new tasks on screen
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("A")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("B")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("C")));

        // Sort alphabetical, we check order of tasks with AZ order
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("A")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("B")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("C")));
    }

    @Test
    public void myTaskList_sortAction_sortTasksAlphabeticalInverted() {
        // Sort alphabetical inverted, we check order of tasks with ZA order
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("C")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("B")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("A")));
    }

    @Test
    public void myTaskList_sortAction_sortTasksOldFirst() {
        // Sort old first, we check order of task with last tasks registered first
        onView(withId(R.id.action_filter)).
                perform(click());
        onView(withText(R.string.sort_oldest_first)).
                perform(click());
        onView(withRecyclerView(R.id.list_tasks).
                atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).
                atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Hhh Tâche example")));
    }

    @Test
    public void myTaskList_sortAction_sortTasksRecentFirst() {
        // Sort recent first, we check order of task with recent tasks registered first
        onView(withId(R.id.action_filter))
                .perform(click());
        onView(withText(R.string.sort_recent_first))
                .perform(click());
        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("Zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("Aaa Tâche example")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

}
