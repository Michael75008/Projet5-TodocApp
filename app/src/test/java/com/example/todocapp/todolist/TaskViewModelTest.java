package com.example.todocapp.todolist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.ui.TaskAdapter;
import com.example.todocapp.utils.LiveDataTestUtil;
import com.example.todocapp.utils.TaskListMapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    TaskAdapter mTaskAdapter;

    @Mock
    TaskViewModel mTaskViewModel;

    private WeakReference<TaskAdapter.Listener> callbackWeakRef;

    ViewModelStore mViewModelStore;

    // FOR DATA
    private ProjectDatabase database;
    private TaskListMapper mTaskListMapper;

    // DATA SET FOR TEST
    private static int TASK_ID = 1;
    private static Task TASK_DEMO = new Task(5, 2, "Demo task name", new Date().getTime());
    private static TaskOnUI TASK_ON_UI_DEMO = new TaskOnUI(5, 0xFFEADAD1, "Projet Tartampion", "Demo task on Ui name");
    private static int TASKLIST_SIZE = 3;
    private static int PROJECTLIST_SIZE = 3;

    @Before
    public void initDb() {
        MockitoAnnotations.initMocks(this);

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
    public void initLists() {
        // Init list of tasks
        LiveData<List<Task>> taskList = mTaskViewModel.initLists();
        //
        assertNotNull(taskList.getValue());
    }

    @Test
    public void getProjectsList() {
        // Init list of projects
        mTaskViewModel.initLists();
        // Create a list throw viewmodel method
        List<Project> projectList = mTaskViewModel.getProjectsList();
        // Check we find all our projects in list
        assertEquals(PROJECTLIST_SIZE, projectList.size());
    }


    @Test
    public void getTasksOnUi() throws InterruptedException {
        // Get our list of tasks from DB
        List<Task> taskListFromDB = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if task list contains our element checking list size
        List<TaskOnUI> taskOnUIList = mTaskViewModel.getTasksOnUi(taskListFromDB, mTaskAdapter);

        assertEquals(TASKLIST_SIZE, taskOnUIList.size());
        assertEquals(taskListFromDB.get(0).getName(), taskOnUIList.get(0).getTaskName());
        assertEquals(taskListFromDB.get(1).getName(), taskOnUIList.get(1).getTaskName());
        assertEquals(taskListFromDB.get(2).getName(), taskOnUIList.get(2).getTaskName());
    }

    @Test
    public void deleteTask() throws InterruptedException {
        // Delete a task throws viewmodel method
        mTaskViewModel.deleteTask(TASK_ID);
        // Get our list of tasks from DB
        List<Task> taskListFromDB = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check we delete one element of the list, should find one element less
        assertEquals(TASKLIST_SIZE - 1, taskListFromDB.size());

    }

    @Test
    public void createTask() throws InterruptedException {
        mTaskViewModel.createTask(TASK_DEMO);
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check if task list contains our element checking list size
        assertEquals(TASKLIST_SIZE + 1, mTaskViewModel.getTasksOnUi(taskList, mTaskAdapter).size());
    }

    @Test
    public void onClickDeleteButton() {
        mTaskViewModel.onClickDeleteButton(TASK_ON_UI_DEMO);
    }

    @Test
    public void onClickAddTaskButton() {
        mTaskViewModel.onClickAddTaskButton(TASK_ON_UI_DEMO);
    }
}