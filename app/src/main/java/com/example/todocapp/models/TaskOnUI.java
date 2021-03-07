package com.example.todocapp.models;

public class TaskOnUI {

    /**
     * The unique identifier of the task (auto)
     */
    private int taskId;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    private int projectColor;

    /**
     * The name of the project
     */
    private String projectName;

    /**
     * The name of the task
     */
    private String taskName;

    // Constructors

    public TaskOnUI(int taskId, int projectColor, String projectName, String taskName) {
        this.taskId = taskId;
        this.projectColor = projectColor;
        this.projectName = projectName;
        this.taskName = taskName;
    }

    public TaskOnUI() { }

    // Getters

    public int getProjectColor() {
        return projectColor;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskId() {
        return taskId;
    }

    // Setters

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public void setProjectColor(int projectColor) {
        this.projectColor = projectColor;
    }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

}
