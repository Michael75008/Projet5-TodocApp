package com.example.todocapp.injections;

import android.content.Context;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context) {
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjectDataRepository provideProjectDataSource(Context context) {
        ProjectDatabase database = ProjectDatabase.getInstance(context);
        return new ProjectDataRepository(database.projectDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjectDataRepository dataSourceProject = provideProjectDataSource(context);
        Executor executor = provideExecutor();
        TaskListMapper taskListMapper = provideTaskListMapper();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor, taskListMapper);
    }

    public static TaskListMapper provideTaskListMapper(){
        return new TaskListMapper();
    }
}
