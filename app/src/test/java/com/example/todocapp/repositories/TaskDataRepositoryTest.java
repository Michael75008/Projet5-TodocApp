package com.example.todocapp.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.TaskDao;
import com.example.todocapp.models.Task;
import com.example.todocapp.utils.LiveDataTestUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TaskDataRepositoryTest {

    @Mock
    private TaskDao taskDao;

    @Mock
    private TaskDataRepository sut;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public TaskDataRepositoryTest() {
    }

    @Before
    public void initDb() {
        MockitoAnnotations.initMocks(this);
        sut = new TaskDataRepository(taskDao);
    }


    @Test
    public void getTasksTest() throws InterruptedException {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime());
        MutableLiveData<List<Task>> fakeLiveData = new MutableLiveData<List<Task>>() {};
        fakeLiveData.setValue(tasks);
        when(taskDao.getTasks()).thenReturn(fakeLiveData);

        sut.createTask(task);

        List<Task> result = LiveDataTestUtil.getValue(sut.getTasks());



        assertEquals(result.size(), tasks.size());
    }

    @Test
    public void createTaskTest() throws InterruptedException {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime());

        List<Task> result = LiveDataTestUtil.getValue(sut.getTasks());

        assertEquals(1, result.size());



/*
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
        assertEquals(TASKLIST_SIZE + 1, taskList.size());*/
    }

    @Test
    public void deleteTaskTask() throws InterruptedException {

      /*  // Create single thread for task creation
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
        assertEquals(TASKLIST_SIZE, taskList.size());*/
    }
}