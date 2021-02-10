package com.example.todocapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.todocapp.database.dao.TaskDao;
import com.example.todocapp.models.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public LiveData<List<Task>> getTasksByAsc() {
        return taskDao.getTasksByAsc();
    }

    public LiveData<List<Task>> getTaskssByDesc() {
        return taskDao.getTasksByDesc();
    }

    public LiveData<List<Task>> getTasksByCreationTimeAsc(){
        return taskDao.getTasksByCreationTimeAsc();
    }

    public LiveData<List<Task>> getTasksByCreationTimeDesc(){
        return taskDao.getTasksByCreationTimeDesc();
    }

    public void createTask(Task task) {
        taskDao.createTask(task);
    }

    public void deleteTask(int taskId) {
        taskDao.deleteTask(taskId);
    }
}
