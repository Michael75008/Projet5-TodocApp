package com.example.todocapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.example.todocapp.models.Project;
import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    List<Project> getProjects();
}
