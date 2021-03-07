package com.example.todocapp.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.todocapp.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTasksList_shouldNotBeEmpty() {
        //Check if the 3 tasks from db are displayed on screen
        onView(allOf(isDisplayed(), ViewMatchers.withId(R.id.list_tasks)))
                .check(matches(hasChildCount(3)));
    }

    @Test
    public void myTasksList_deleteTaskAction_shouldDisplayMessageForEmptyList() {
        //Delete action for our 3 tasks
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
        //Check if we see message for empty list on screen
        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_no_task), withText("Tu n’as aucune tâche à traiter"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void myTaskList_createTaskAction_shouldDisplayProjectChoices() {
        // Click on fab to create a new task
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());
        // Click on spinner to see options for task's project affectation
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.project_spinner),
                        childAtPosition(
                                allOf(withId(R.id.linear_layout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());
        // Should display our 3 project options on screen
        ViewInteraction checkedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Projet Tartampion"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

    }

    @Test
    public void myTasksList_createTaskAction_shouldAddNewTaskToUi() {
        //Click on fab to add new task
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());
        //Enter the name of the new task "Courir deux heures"
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.txt_task_name),
                        childAtPosition(
                                allOf(withId(R.id.linear_layout),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Courir deux heures"), closeSoftKeyboard());
        //Click on add button to add new task to the list
        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Ajouter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());
        //Check if we found our new task on screen
        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_task_name), withText("Courir deux heures"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    @Test
    public void myTasksList_deleteTaskAction_shouldDeleteNewTaskOnUi() {
        // Delete action on our new task
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
        //Check if we see message for empty list on screen again after deleting this task
        ViewInteraction textView = onView(
                allOf(withId(R.id.lbl_no_task), withText("Tu n’as aucune tâche à traiter"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
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
