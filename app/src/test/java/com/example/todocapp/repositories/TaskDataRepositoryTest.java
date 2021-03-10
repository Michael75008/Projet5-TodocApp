package com.example.todocapp.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.database.dao.TaskDao;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.models.Task;
import com.example.todocapp.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TaskDataRepositoryTest {

    // FOR DATA
    private ProjectDatabase database;
    private Executor executor;
    // DATA SET FOR TEST
    private static int TASK_ID = 5;
    private static Task TASK_DEMO = new Task(TASK_ID, 2, "Demo task name", new Date().getTime());
    private static int TASKLIST_SIZE = 3;

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
    public void getTasksTest() throws InterruptedException {
        // Create DAO from DB
        TaskDao taskDao = database.taskDao();
        // Create a new task data repository throw Dao
        TaskDataRepository taskDataRepository = new TaskDataRepository(taskDao);
        // Get our list of tasks from DB throw Repo, task list expected
        List<Task> taskListExpected = LiveDataTestUtil.getValue(taskDataRepository.getTasks());
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if we find the same list size for both task list from repo and from task list db
        assertEquals(taskListExpected.size(), taskList.size());
        // Check one by one our 3 tasks's names from each list
        assertEquals(taskListExpected.get(0).getName(), taskList.get(0).getName());
        assertEquals(taskListExpected.get(1).getName(), taskList.get(1).getName());
        assertEquals(taskListExpected.get(2).getName(), taskList.get(2).getName());
    }

    @Test
    public void createTaskTest() throws InterruptedException {
        // Create single thread for task creation
        executor = Injection.provideExecutor();
        // Create DAO from DB
        TaskDao taskDao = database.taskDao();
        // Create a new task data repository throw Dao
        TaskDataRepository taskDataRepository = new TaskDataRepository(taskDao);
        // Create a new task with repo throw executor
        executor.execute(() -> taskDataRepository.createTask(TASK_DEMO));
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if task list contains our element checking list size
        assertEquals(TASKLIST_SIZE + 1, taskList.size());
    }

    @Test
    public void deleteTaskTask() throws InterruptedException {
        // Create single thread for task creation
        executor = Injection.provideExecutor();
        // Create DAO from DB
        TaskDao taskDao = database.taskDao();
        // Create a new task data repository throw Dao
        TaskDataRepository taskDataRepository = new TaskDataRepository(taskDao);
        // Delete our new task with repo throw executor
        executor.execute(() -> taskDataRepository.deleteTask(TASK_ID));
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if task list contains our element checking list size
        assertEquals(TASKLIST_SIZE, taskList.size());
    }

    @After
    public void closeDb() {
        database.close();
    }
}