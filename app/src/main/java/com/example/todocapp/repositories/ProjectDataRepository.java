package com.example.todocapp.repositories;

import android.database.Cursor;

import androidx.lifecycle.LiveData;

import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.models.Project;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }
}
