package com.example.todocapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.todocapp.database.dao.TaskDao;
import com.example.todocapp.models.Task;

import java.util.List;

public class TaskDataRepository {

    // For Data

    private final TaskDao taskDao;

    // Constructor

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // Methods

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public void createTask(Task task) {
        taskDao.createTask(task);
    }

    public void deleteTask(int taskId) {
        taskDao.deleteTask(taskId);
    }
}
