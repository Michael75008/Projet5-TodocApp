package com.example.todocapp.models;

public class TaskOnUI {


    private int projectColor;

    private String projectName;

    private String taskName;


    public TaskOnUI(int projectColor, String projectName, String taskName) {
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

}
