package com.example.todocapp.ui;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.todocapp.utils.RecyclerViewMatcher.childAtPosition;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class AddTaskInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void myTasksList_createTaskAction_shouldAddNewTaskToUi() {
        //Click on fab to add new task
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
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

        appCompatEditText.perform(replaceText("D"), closeSoftKeyboard());
        //Click on add button to add new task to the list
        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("Ajouter"),
                       isDisplayed()));
        materialButton.perform(scrollTo(), click());
        //Check if we found our new task on screen
        ViewInteraction textView = onView(
                allOf(withText("D"),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
        // Delete action on our new task
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.img_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_tasks),
                                        3),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

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
          checkViewWithString("Projet Tartampion");
          checkViewWithString("Projet Lucidia");
          checkViewWithString("Projet Circus");
      }

      private void checkViewWithString(String name) {
          onView(withText(containsString(name))).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
      }
}
