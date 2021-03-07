package com.example.todocapp.models;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Project {

    /**
     * The unique identifier of the project
     */
    @PrimaryKey
    private int projectId;

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

    // Constructors

    public Project(int projectId, String name, int color) {
        this.projectId = projectId;
        this.name = name;
        this.color = color;
    }

    @Ignore
    public Project() {
    }

    // Getters

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    // Setters

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }


    @Override
    public String toString() {
        return name;
    }

    //Method creating new Project based on content values process
    public ContentValues toContentValue() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("projectId", this.projectId);
        contentValues.put("name", this.name);
        contentValues.put("color", this.color);
        return contentValues;
    }
}
