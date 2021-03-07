package com.example.todocapp.database.dao;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;

import java.util.Calendar;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {

    private static volatile ProjectDatabase INSTANCE;

    protected ProjectDatabase() {
    }

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();

    public static ProjectDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ProjectDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    ProjectDatabase.class, "MyDatabase.db")
                                    .addCallback(prepopulateDataBase())
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback prepopulateDataBase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                //Create 3 tasks
                ContentValues contentValues6 = new Task(1, (int) 3L, "C", Calendar.getInstance().getTime().getTime()).toContentValue();
                ContentValues contentValues5 = new Task(2, (int) 1L, "B", Calendar.getInstance().getTime().getTime()).toContentValue();
                ContentValues contentValues4 = new Task(3, (int) 2L, "A", Calendar.getInstance().getTime().getTime()).toContentValue();
                //Create 3 projects
                ContentValues contentValues3 = new Project((int) 1L, "Projet Tartampion", 0xFFEADAD1).toContentValue();
                ContentValues contentValues2 = new Project((int) 2L, "Projet Lucidia", 0xFFB4CDBA).toContentValue();
                ContentValues contentValues = new Project((int) 3L, "Projet Circus", 0xFFA3CED2).toContentValue();
                //Insert objects on DB
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);
                db.insert("Task", OnConflictStrategy.IGNORE, contentValues4);
                db.insert("Task", OnConflictStrategy.IGNORE, contentValues5);
                db.insert("Task", OnConflictStrategy.IGNORE, contentValues6);
            }
        };
    }
}
