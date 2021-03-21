package com.example.todocapp.database.dao;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.models.Task;
import com.example.todocapp.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest  {

    // FOR DATA
    private ProjectDatabase database;
    // DATA SET FOR TEST
    private static int TASKLIST_SIZE = 3;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(fakeDataBase())
                        .build();
    }

    @Test
    public void getTaskListFromDatabaseWithSuccess() throws InterruptedException {
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that the list is not empty
        assertFalse(taskList.isEmpty());
        // Assert that task list contains all the elements (3 tasks) of database
        assertEquals(taskList.size(), TASKLIST_SIZE);
    }

    @Test
    public void createTaskAndGetItOnList() throws InterruptedException {
        Task task = new Task(4, 2, "Demo task name", new Date().getTime());
        // Creates a new demo task
        this.database.taskDao().createTask(task);
        // Get task list from db
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that we added a new task to the list
        assertEquals(taskList.size(), TASKLIST_SIZE + 1);
    }

    @Test
    public void deleteTasksFromListWithTaskID() throws InterruptedException {
        // Deletes task with id 1 from list
        this.database.taskDao().deleteTask(1);
        // Get task list from db
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());
        // Assert that we should find 3 tasks again
        assertEquals("list should counts 2 tasks", taskList.size(), TASKLIST_SIZE - 1);
    }

    private static RoomDatabase.Callback fakeDataBase() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                //Create 3 tasks
                ContentValues contentValues6 = new Task(1, (int) 3L, "C", mDate("01-01-2021 12:00:00").getTime()).toContentValue();
                ContentValues contentValues5 = new Task(2, (int) 1L, "B", mDate("01-01-2021 10:00:00").getTime()).toContentValue();
                ContentValues contentValues4 = new Task(3, (int) 2L, "A", mDate("01-01-2021 08:00:00").getTime()).toContentValue();

                db.insert("Task", OnConflictStrategy.IGNORE, contentValues4);
                db.insert("Task", OnConflictStrategy.IGNORE, contentValues5);
                db.insert("Task", OnConflictStrategy.IGNORE, contentValues6);
            }
        };
    }

    private static Date mDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE);
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }


    @After
    public void closeDb() {
        database.close();
    }
}
