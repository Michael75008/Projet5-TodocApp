package com.example.todocapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import android.database.Cursor;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    Cursor getProjects();
}
