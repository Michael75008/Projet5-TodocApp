package com.example.todocapp.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todocapp.models.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    Cursor getTasks();

    @Insert
    long createTask(Task task);


    @Query("DELETE FROM Task WHERE taskId = :taskId")
    int deleteTask(long taskId);


}
