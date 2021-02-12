package com.example.todocapp.models;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class Task {

    /**
     * The unique identifier of the task
     */
    @PrimaryKey
    private int taskId;

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

    public Task(int taskId, long projectId, String name, long creationTimestamp) {
        this.taskId = taskId;
        this.projectId = projectId;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    @Ignore
    public Task() {

    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
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
        if (values.containsKey("taskId")) task.setTaskId(values.getAsInteger("taskId"));
        if (values.containsKey("name")) task.setName(values.getAsString("name"));
        if (values.containsKey("projectId")) task.setProjectId(values.getAsLong("projectId"));
        if (values.containsKey("creationTime"))
            task.setCreationTimestamp(values.getAsLong("creationTime"));
        return task;
    }

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
