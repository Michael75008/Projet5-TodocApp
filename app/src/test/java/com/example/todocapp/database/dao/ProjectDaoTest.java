package com.example.todocapp.database.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.models.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public final class ProjectDaoTest {

    // FOR DATA
    private ProjectDatabase database;
    Context mContext;
    // DATA SET FOR TEST
    private static int PROJECTLIST_SIZE = 3;

    @Before
    public void initDb() {
        mContext = ApplicationProvider.getApplicationContext();
        this.database =
                Room.inMemoryDatabaseBuilder(mContext, ProjectDatabase.class)
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