package com.example.todocapp.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todocapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTasks();

    @Insert
    long createTask(Task task);


    @Query("DELETE FROM TASK WHERE taskId = :taskId")
    void deleteTask(int taskId);

    @Query("SELECT * FROM TASK ORDER BY name ASC")
    LiveData<List<Task>> getTasksByAsc();

    @Query("SELECT * FROM TASK ORDER BY name DESC")
    LiveData<List<Task>> getTasksByDesc();

    @Query("SELECT * FROM TASK ORDER BY creationTimestamp ASC")
    LiveData<List<Task>> getTasksByCreationTimeAsc();

    @Query("SELECT * FROM TASK ORDER BY creationTimestamp DESC")
    LiveData<List<Task>> getTasksByCreationTimeDesc();
}

