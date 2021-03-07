package com.example.todocapp.injections;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

@RunWith(AndroidJUnit4.class)
public class InjectionTest {



    private TaskDataRepository mTaskDataRepository;

    private ProjectDataRepository mProjectDataRepository;

    private Executor mExecutor;

    private ViewModelFactory mViewModelFactory;

    private TaskListMapper mTaskListMapper;

    @Test
    public void provideTaskDataSource() {
        Injection.provideTaskDataSource(getApplicationContext());

        ProjectDatabase database = ProjectDatabase.getInstance(getApplicationContext());

    }

    @Test
    public void provideProjectDataSource() {
    }

    @Test
    public void provideExecutor() {
    }

    @Test
    public void provideViewModelFactory() {
    }

    @Test
    public void provideTaskListMapper() {
    }
}