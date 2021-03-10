package com.example.todocapp.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ProjectDataRepositoryTest {

    // FOR DATA
    private ProjectDatabase database;

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(prepopulateDataBase())
                        .build();
    }

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Test
    public void getProjectsTest() {
        // Create DAO from DB
        ProjectDao projectDao = database.projectDao();
        // Create a new project data repository throw Dao
        ProjectDataRepository projectDataRepository = new ProjectDataRepository(projectDao);
        // Create list from repo with our method getProjects
        List<Project> projectList = projectDataRepository.getProjects();
        // Check if we find the same list size for both projectList and projectDao
        assertEquals(projectList.size(), projectDao.getProjects().size());
        // Check one by one our 3 project's names from each list
        assertEquals(projectList.get(0).getName(), projectDao.getProjects().get(0).getName());
        assertEquals(projectList.get(1).getName(), projectDao.getProjects().get(1).getName());
        assertEquals(projectList.get(2).getName(), projectDao.getProjects().get(2).getName());
    }

    @After
    public void closeDb() {
        database.close();
    }
}