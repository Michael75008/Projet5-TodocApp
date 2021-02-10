package com.example.todocapp.models;

public class TaskOnUI {

    private int taskId;

    private int projectColor;

    private String projectName;

    private String taskName;


    public TaskOnUI(int taskId, int projectColor, String projectName, String taskName) {
        this.taskId = taskId;
        this.projectColor = projectColor;
        this.projectName = projectName;
        this.taskName = taskName;
    }

    public TaskOnUI() {

    }

    public int getProjectColor() {
        return projectColor;
    }

    public void setProjectColor(int projectColor) {
        this.projectColor = projectColor;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

}
