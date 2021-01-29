package com.example.todocapp.repositories;


import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.TaskOnUI;

import java.util.List;

public class ProjectDataRepository {

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public List<Project> getProjects() {
        return (List<Project>) this.projectDao.getProjects();
    }
}
