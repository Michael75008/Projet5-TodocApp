package com.example.todocapp.repositories;


import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.models.Project;

import java.util.List;

public class ProjectDataRepository {

    // For Data

    private final ProjectDao projectDao;

    // Constructor

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // Method

    public List<Project> getProjects() {
        return (List<Project>) this.projectDao.getProjects();
    }
}
