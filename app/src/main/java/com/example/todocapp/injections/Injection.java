package com.example.todocapp.injections;

import android.content.Context;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    // Injection access to TaskDB

    public static TaskDataRepository provideTaskDataSource(Context context) {
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    // Injection access to ProjectDB

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    // Injection access to Executor

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    // Injection access to ViewModel

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        TaskListMapper taskListMapper = provideTaskListMapper();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor, taskListMapper);
    }

    // Injection access to TaskListMapper

    public static TaskListMapper provideTaskListMapper() {
        return new TaskListMapper();
    }
}
