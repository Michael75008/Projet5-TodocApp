package com.example.todocapp.todolist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;

    @Nullable
    private List<TaskOnUI> tasksList;


    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.tasksList != null) {
            return;
        }
        tasksList = getTasksList();
    }

    public List<TaskOnUI> getTasksList() {
        List<TaskOnUI> currentTasks = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(taskDataSource.getTasks().getValue()).size(); i++) {
            Task task = taskDataSource.getTasks().getValue().get(i);
            Project project = projectDataSource.getProjects().get(i);
            if (project.getId() == task.getProjectId()) {
                TaskOnUI taskOnUI = new TaskOnUI();
                taskOnUI.setTaskName(task.getName());
                taskOnUI.setProjectColor(project.getColor());
                taskOnUI.setProjectName(project.getName());
                currentTasks.add(taskOnUI);
            }
        }
        return currentTasks;
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }
}


