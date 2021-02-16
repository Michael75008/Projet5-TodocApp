package com.example.todocapp.database.dao;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    private static Callback prepopulateDataBase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues6 = new ContentValues();
                ContentValues contentValues5 = new ContentValues();
                ContentValues contentValues4 = new ContentValues();
                ContentValues contentValues3 = new ContentValues();
                ContentValues contentValues2 = new ContentValues();
                ContentValues contentValues = new ContentValues();

                contentValues.put("projectId", 1L);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);

                contentValues2.put("projectId", 2L);
                contentValues2.put("name", "Projet Lucidia");
                contentValues2.put("color", 0xFFB4CDBA);

                contentValues3.put("projectId", 3L);
                contentValues3.put("name", "Projet Circus");
                contentValues3.put("color", 0xFFA3CED2);

                contentValues4.put("projectId",2L);
                contentValues4.put("name", "A");
                contentValues4.put("creationTimeStamp", Calendar.getInstance().getTimeInMillis());

                contentValues5.put("projectId",1L);
                contentValues5.put("name", "B");
                contentValues5.put("creationTimeStamp", Calendar.getInstance().getTimeInMillis());

                contentValues6.put("projectId",3L);
                contentValues6.put("name", "C");
                contentValues6.put("creationTimeStamp", Calendar.getInstance().getTimeInMillis());

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
