package com.example.todocapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import android.database.Cursor;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData <List<Project>> getProjects();
}
