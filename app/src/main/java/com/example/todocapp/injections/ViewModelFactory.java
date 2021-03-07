package com.example.todocapp.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.todolist.TaskViewModel;
import com.example.todocapp.utils.TaskListMapper;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    // For init our design pattern

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;
    private final TaskListMapper mTaskListMapper;

    // Constructor

    public ViewModelFactory(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor, TaskListMapper taskListMapper) {
        this.taskDataSource = taskDataRepository;
        this.projectDataSource = projectDataRepository;
        this.executor = executor;
        this.mTaskListMapper = taskListMapper;
    }

    // Instantiation of our ViewModel throw ViewModelFactory

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, projectDataSource, executor, mTaskListMapper);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
