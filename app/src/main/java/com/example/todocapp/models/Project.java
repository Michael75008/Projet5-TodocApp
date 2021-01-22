package com.example.todocapp.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.content.ContentValues;

import androidx.annotation.NonNull;

@Entity
public class Project {


    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    private long Id;

    /**
     * The name of the project
     */
    @NonNull
    private String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @NonNull
    private int color;

    /**
     * Instantiates a new Project
     */

    public Project(long projectId, String name, int color) {
        this.Id = projectId;
        this.name = name;
        this.color = color;
    }

    public Project() {

    }

    //GETTER

    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    //SETTER
    public void setId(long id) {
        this.Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    // --- UTILS ---
    public static Project fromContentValues(ContentValues values) {
        final Project project = new Project();
        if (values.containsKey("projectName")) project.setName(values.getAsString("projectName"));
        if (values.containsKey("projectColor")) project.setColor(values.getAsInteger("projectColor"));
        if(values.containsKey("projectId")) project.setId(values.getAsLong("projectId"));
        return project;
    }
}
