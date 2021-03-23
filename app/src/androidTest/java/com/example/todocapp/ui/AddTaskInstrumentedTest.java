package com.example.todocapp.ui;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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
    public void myTaskList_createTaskAction_shouldDisplayErrorMessageWhenNoNameWritten() {
        openAddTaskDialog();
        onView(allOf(withId(android.R.id.button1), withText("Ajouter"))).perform(scrollTo(), click());
        onView(allOf(withId(R.id.txt_task_name), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.txt_task_name), isDisplayed())).check(matches(hasErrorText("Le nom de la tâche doit être renseigné")));
    }

    @Test
    public void myTasksList_createTaskAction_shouldAddNewTaskToUiToList() {
        openAddTaskDialog();
        onView(allOf(withId(R.id.txt_task_name), isDisplayed())).perform(replaceText("D"), closeSoftKeyboard());
        onView(allOf(withId(android.R.id.button1), withText("Ajouter"), isDisplayed())).perform(scrollTo(), click());

        onView(allOf(withText("D"), isDisplayed())).check(matches(isDisplayed()));

        onClickDeleteButtonAtIndex();
    }

    @Test
    public void myTaskList_createTaskAction_shouldDisplayProjectChoicesOnScreen() {
        openAddTaskDialog();
        onView(allOf(withId(R.id.project_spinner), isDisplayed())).perform(click());

        checkViewWithString("Projet Tartampion");
        checkViewWithString("Projet Lucidia");
        checkViewWithString("Projet Circus");
    }

    private void checkViewWithString(String name) {
        onView(withText(containsString(name))).inRoot(isPlatformPopup()).check(matches(isDisplayed()));
    }

    private void openAddTaskDialog() {
        onView(allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                isDisplayed())).perform(click());
    }

    private void onClickDeleteButtonAtIndex() {
        onView(allOf(withId(R.id.img_delete), childAtPosition(
                childAtPosition(
                        withId(R.id.list_tasks),
                        3),
                1),
                isDisplayed())).perform(click());
    }
}
