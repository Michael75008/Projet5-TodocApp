package com.example.todocapp.database.dao;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import com.example.todocapp.models.Task;
import com.example.todocapp.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(RobolectricTestRunner.class)
public class TaskDaoTest  {

    // FOR DATA
    private ProjectDatabase database;
    // DATA SET FOR TEST
    private static int TASK_ID = 5;
    private static int TASKLIST_SIZE = 3;
    private static Task TASK_DEMO = new Task(TASK_ID, 2, "Demo task name", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(prepopulateDataBase())
                        .build();
    }

    @Test
    public void getTaskListFromDatabaseWithSuccess() throws InterruptedException {
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that the list is not empty
        assertFalse(taskList.isEmpty());
        // Assert that task list contains all the elements (3 tasks) of database
        assertEquals(taskList.size(), TASKLIST_SIZE);
    }

    @Test
    public void createTaskAndGetItOnList() throws InterruptedException {
        // Creates a new demo task
        this.database.taskDao().createTask(TASK_DEMO);
        // Get task list from db
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that we added a new task to the list
        assertEquals(taskList.size(), TASKLIST_SIZE + 1);
    }

    @Test
    public void deleteTasksFromListWithTaskID() throws InterruptedException {
        // Deletes task with id 1 from list
        this.database.taskDao().deleteTask(1);
        // Get task list from db
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that we should find 3 tasks again
        assertEquals("list should counts 2 tasks", taskList.size(), TASKLIST_SIZE - 1);
    }

    @After
    public void closeDb() {
        database.close();
    }
}
