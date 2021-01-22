package com.example.todocapp;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private ProjectDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database =
                Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }
}