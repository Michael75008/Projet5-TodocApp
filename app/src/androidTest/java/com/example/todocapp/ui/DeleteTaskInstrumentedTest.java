package com.example.todocapp.ui;

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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
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
        checkItemsCountWith(3);

        onClickDeleteButtonAtIndex(0);

        checkItemsCountWith(2);

        createTaskOnUi("C");
    }

    @Test
    public void myTasksList_deleteTaskAction_shouldDisplayMessageOnlyWhenNoTasks() {
        onClickDeleteButtonAtIndex(2);
        onClickDeleteButtonAtIndex(1);
        onClickDeleteButtonAtIndex(0);

        onView(allOf(withId(R.id.lbl_no_task), withText("Tu n’as aucune tâche à traiter"))).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void afterTest() {
        createTaskOnUi("A");
        createTaskOnUi("B");
        createTaskOnUi("C");
    }

    private void onClickDeleteButtonAtIndex(int position) {
        onView(allOf(withId(R.id.img_delete), childAtPosition(
                childAtPosition(
                        withId(R.id.list_tasks),
                        position),
                1),
                isDisplayed())).perform(click());
    }

    private static void createTaskOnUi(String name) {
        onView(allOf(withId(R.id.fab_add_task), withContentDescription("Ajouter une tâche"),
                isDisplayed())).perform(click());

        onView(allOf(withId(R.id.txt_task_name), isDisplayed())).perform(replaceText(name), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1), withText("Ajouter"))).perform(scrollTo(), click());
    }

    private void checkItemsCountWith(int count) {
        onView(allOf(isDisplayed(), ViewMatchers.withId(R.id.list_tasks)))
                .check(matches(hasChildCount(count)));
    }
}