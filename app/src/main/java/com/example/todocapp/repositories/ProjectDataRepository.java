package com.example.todocapp.repositories;

import android.database.Cursor;
import com.example.todocapp.database.dao.ProjectDao;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public Cursor getProjects() {
        return this.projectDao.getProjects();
    }
}
