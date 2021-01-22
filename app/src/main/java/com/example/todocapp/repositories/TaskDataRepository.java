package com.example.todocapp.repositories;

import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.example.todocapp.database.dao.TaskDao;
import com.example.todocapp.models.Task;

import java.util.List;

import javax.crypto.Cipher;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public Cursor getTasks() {
        return this.taskDao.getTasks();
    }

    public void createTask(Task task) {
        taskDao.createTask(task);
    }

    public void deleteTask(long taskId) {
        taskDao.deleteTask(taskId);
    }

}
