package com.example.todocapp.utils;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListMapper {

    // Constructor

    public TaskListMapper() {}

    // Get task's list and convert it on a TaskOnUi's list

    public List<TaskOnUI> getTaskAsTaskOnUiList(List<Task> tasksList, List<Project> projectList) {
        List<TaskOnUI> currentTasks = new ArrayList<>();
        if (tasksList == null || tasksList.size() == 0) return currentTasks;
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            for (int i2 = 0; i2 < projectList.size(); i2++) {
                Project project = projectList.get(i2);
                if (project.getProjectId() == task.getProjectId()) {
                    TaskOnUI taskOnUI = new TaskOnUI();
                    taskOnUI.setTaskId(task.getTaskId());
                    taskOnUI.setTaskName(task.getName());
                    taskOnUI.setProjectColor(project.getColor());
                    taskOnUI.setProjectName(project.getName());
                    currentTasks.add(taskOnUI);
                }
            }
        }
        return currentTasks;
    }

    // Get taskOnUi and convert it on a Task

    public Task getTaskFromTaskUi(TaskOnUI taskOnUI, List<Project> projectList) {
        Task task = new Task();
        task.setCreationTimestamp(new Date().getTime());
        task.setName(taskOnUI.getTaskName());
        for (int i2 = 0; i2 < projectList.size(); i2++) {
            Project project = projectList.get(i2);
            if (project.getName().equals(taskOnUI.getProjectName())) {
                task.setProjectId(project.getProjectId());
            }
        }
        return task;
    }
}


