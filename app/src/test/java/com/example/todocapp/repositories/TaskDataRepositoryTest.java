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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TaskDataRepositoryTest {

    @Mock
    private TaskDao taskDao;

    @Mock
    private TaskDataRepository sut;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new TaskDataRepository(taskDao);
    }

    @Test
    public void getTasksTest() throws InterruptedException {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime());
        tasks.add(task);
        MutableLiveData<List<Task>> fakeLiveData = new MutableLiveData<List<Task>>() {};
        fakeLiveData.setValue(tasks);
        when(taskDao.getTasks()).thenReturn(fakeLiveData);

        List<Task> result = LiveDataTestUtil.getValue(sut.getTasks());

        assertEquals(result.size(), tasks.size());
        verify(taskDao, times(1)).getTasks();
    }

    @Test
    public void createTaskTest() {
        Task task = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime());

        sut.createTask(task);

        verify(taskDao, times(1)).createTask(task);
    }

    @Test
    public void deleteTaskTask() {
        Task task = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime());

        sut.deleteTask(task.getTaskId());

        verify(taskDao, times(1)).deleteTask(task.getTaskId());
    }
}