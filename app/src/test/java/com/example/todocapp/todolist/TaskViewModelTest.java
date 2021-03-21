package com.example.todocapp.todolist;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelStore;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.injections.ViewModelFactory;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.SortMethod;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.ui.TaskAdapter;
import com.example.todocapp.utils.LiveDataTestUtil;
import com.example.todocapp.utils.TaskListMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    Context mContext;

    @Mock
    private ProjectDao projectDao;

    @Mock
    private TaskDataRepository sut;


    private ViewModelFactory mViewModelFactory;

    private TaskViewModel mTaskViewModel;


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

        mTaskViewModel = mock(TaskViewModel.class);
        when(mTaskViewModel.initLists()).thenReturn(database.taskDao().getTasks());
        when(mTaskViewModel.getProjectsList()).thenReturn(database.projectDao().getProjects());
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
/*
        assertEquals(TASKLIST_SIZE, taskOnUIList.size());
        assertEquals(taskListFromDB.get(0).getName(), taskOnUIList.get(0).getTaskName());
        assertEquals(taskListFromDB.get(1).getName(), taskOnUIList.get(1).getTaskName());
        assertEquals(taskListFromDB.get(2).getName(), taskOnUIList.get(2).getTaskName());*/
    }

    @Test
    public void displaySorter_sortAlphabetical() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, (int) 1L, "A", mDate("01-01-2021 12:00:00").getTime());
        Task task2 = new Task(2, (int) 1L, "B", mDate("02-01-2021 12:00:00").getTime());
        tasks.add(task2);
        tasks.add(task1);

        mTaskViewModel.mSortMethod = SortMethod.ALPHABETICAL;
        mTaskViewModel.sortTasks(tasks);

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);

    }

    @Test
    public void displaySorter_sortAlphabeticalInverted() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, (int) 1L, "A", mDate("01-01-2021 12:00:00").getTime());
        Task task2 = new Task(2, (int) 1L, "B", mDate("02-01-2021 12:00:00").getTime());
        tasks.add(task1);
        tasks.add(task2);

        mTaskViewModel.mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
        mTaskViewModel.sortTasks(tasks);

        assertSame(tasks.get(1), task1);
        assertSame(tasks.get(0), task2);
    }


    @Test
    public void deleteTask() throws InterruptedException {
        // Delete a task throws viewmodel method
        // Get our list of tasks from DB
        List<Task> taskListFromDB = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Check we delete one element of the list, should find one element less
        assertEquals(TASKLIST_SIZE - 1, taskListFromDB.size());

    }

    @Test
    public void createTask() throws InterruptedException {

    }

    @Test
    public void onClickDeleteButton() {
        mTaskViewModel.onClickDeleteButton(TASK_ON_UI_DEMO);
    }

    @Test
    public void onClickAddTaskButton() {
        mTaskViewModel.onClickAddTaskButton(TASK_ON_UI_DEMO);
    }

    private static Date mDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE);
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }
}