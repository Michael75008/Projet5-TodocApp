package com.example.todocapp.models;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "projectId",
        childColumns = "projectId"))
public class Task {

    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    private long taskId;

    /**
     * The unique identifier of the project associated to the task
     */
    @NonNull
    private long projectId;

    /**
     * The name of the task
     */
    @NonNull
    private String name;

    /**
     * The timestamp when the task has been created
     */
    @NonNull
    private long creationTimestamp;

    public Task(long taskId, long projectId, String name, long creationTimestamp) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    public Task() {

    }
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    // --- UTILS ---
    public static Task fromContentValues(ContentValues values) {
        final Task task = new Task();
        if(values.containsKey("taskId")) task.setTaskId(values.getAsLong("taskId"));
        if (values.containsKey("name")) task.setName(values.getAsString("name"));
        if (values.containsKey("projectId")) task.setProjectId(values.getAsLong("projectId"));
        if(values.containsKey("creationTime")) task.setCreationTimestamp(values.getAsLong("creationTime"));
        return task;
    }
}
