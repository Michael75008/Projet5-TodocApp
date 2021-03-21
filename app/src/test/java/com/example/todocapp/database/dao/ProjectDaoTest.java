package com.example.todocapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.models.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
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
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(fakeDataBase())
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

    private static RoomDatabase.Callback fakeDataBase() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ContentValues contentValues3 = new Project((int) 1L, "Projet Tartampion", 0xFFEADAD1).toContentValue();
                ContentValues contentValues2 = new Project((int) 2L, "Projet Lucidia", 0xFFB4CDBA).toContentValue();
                ContentValues contentValues = new Project((int) 3L, "Projet Circus", 0xFFA3CED2).toContentValue();

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);
            }
        };
    }

    @After
    public void closeDb() {
        database.close();
    }
}