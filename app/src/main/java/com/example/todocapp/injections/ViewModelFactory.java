package com.example.todocapp.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.todolist.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    public ViewModelFactory(TaskDataRepository taskDataRepository, ProjectDataRepository projectDataRepository, Executor executor) {
        this.taskDataSource = taskDataRepository;
        this.projectDataSource = projectDataRepository;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(taskDataSource, projectDataSource, executor);
        }
        throw new IllegalArgumentException("Unknow ViewModel class");
    }
}
