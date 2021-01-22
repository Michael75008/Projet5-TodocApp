package com.example.todocapp.todolist;

import android.database.Cursor;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.example.todocapp.models.Task;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;

import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    @Nullable
    private Cursor tasksList;


    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.tasksList != null) {
            return;
        }
        tasksList = taskDataSource.getTasks();
    }

    public Cursor getTasksList() {
        return this.taskDataSource.getTasks();
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }
}


