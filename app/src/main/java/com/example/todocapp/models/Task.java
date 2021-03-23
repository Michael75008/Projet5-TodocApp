package com.example.todocapp.models;

import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class Task {

    /**
     * The unique identifier of the task (auto)
     */
    @PrimaryKey(autoGenerate = true)
    private int taskId;

    /**
     * The unique identifier of the project associated to the task
     */
    private int projectId;

    /**
     * The name of the task
     */
    private String name;

    /**
     * The timestamp when the task has been created
     */
    private long creationTimestamp;

    // Constructors

    public Task(int taskId, int projectId, String name, long creationTimestamp) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    @Ignore
    public Task() { }


    // Getters

    public int getTaskId() {
        return taskId;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    // Setters

    public void setProjectId(int projectId) { this.projectId = projectId; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    //Method creating new Task based on content values process

    public ContentValues toContentValue(){
        final ContentValues contentValues = new ContentValues();
        contentValues.put("taskId", this.taskId);
        contentValues.put("name", this.name);
        contentValues.put("projectId", this.projectId);
        contentValues.put("creationTimeStamp", this.creationTimestamp);
        return contentValues;
    }

    // Comparators

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}
