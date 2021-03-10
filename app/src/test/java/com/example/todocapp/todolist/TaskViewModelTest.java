package com.example.todocapp.todolist;

import androidx.room.Room;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class TaskViewModelTest {


    // FOR DATA
    private ProjectDatabase database;
    TaskViewModel mTaskViewModel;
    // DATA SET FOR TEST
    private static int TASK_ID = 5;
    private static Task TASK_DEMO = new Task(TASK_ID, 2, "Demo task name", new Date().getTime());
    private static int TASKLIST_SIZE = 3;



    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(prepopulateDataBase())
                        .build();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getProjectsList() {
    }


    @Test
    public void getTasksOnUi() {
    }

    @Test
    public void createTask() throws InterruptedException {

        TaskOnUI TASK_ON_UI_DEMO = new TaskOnUI(TASK_ID, 0xFFEADAD1,database.projectDao().getProjects().get(1).getName() , "Hello World");

        mTaskViewModel.onClickAddTaskButton(TASK_ON_UI_DEMO);

        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if task list contains our element checking list size
        assertEquals(TASKLIST_SIZE + 1, taskList.size());

    }

    @Test
    public void deleteTask() {
        // Create single thread for task creation
    }

    @Test
    public void onClickDeleteButton() {
    }

    @Test
    public void onClickAddTaskButton() {
    }
}