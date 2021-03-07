package com.example.todocapp.database.dao;

import androidx.room.Room;

import com.example.todocapp.models.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class ProjectDaoTest {

    // FOR DATA
    private ProjectDatabase database;
    // DATA SET FOR TEST
    private static int PROJECTLIST_SIZE = 3;

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(prepopulateDataBase())
                        .build();
    }

    @Test
    public void getProjects() {
        // Get Project list from database
        List<Project> projectList = this.database.projectDao().getProjects();
        // Assert that the list is not empty
        assertFalse("list should not be empty", projectList.isEmpty());
        // Assert that task list contains all the elements (3 projects) of database
        assertEquals("list should contain three projects", projectList.size(), PROJECTLIST_SIZE);
    }

    @After
    public void closeDb() {
        database.close();
    }
}