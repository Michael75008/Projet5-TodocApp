package com.example.todocapp.database.dao;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {

    private static volatile ProjectDatabase INSTANCE;

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

                ContentValues contentValues3 = new ContentValues();
                ContentValues contentValues2 = new ContentValues();
                ContentValues contentValues = new ContentValues();

                contentValues.put("id", 1L);
                contentValues.put("name", "Projet Tartampion");
                contentValues.put("color", 0xFFEADAD1);

                contentValues2.put("id", 2L);
                contentValues2.put("name", "Projet Lucidia");
                contentValues2.put("color", 0xFFB4CDBA);

                contentValues3.put("id", 3L);
                contentValues3.put("name", "Projet Circus");
                contentValues3.put("color", 0xFFA3CED2);

                db.insert("Project", OnConflictStrategy.IGNORE, contentValues);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues2);
                db.insert("Project", OnConflictStrategy.IGNORE, contentValues3);


            }
        };
    }

}
