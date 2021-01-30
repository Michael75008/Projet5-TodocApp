package com.example.todocapp.todolist;

import android.widget.ArrayAdapter;

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
    private List<Project> projectList;

    @Nullable
    private LiveData<List<Task>> tasksList;


    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        if (this.tasksList != null) {
            return;
        }
        tasksList = taskDataSource.getTasks();
        executor.execute(()-> projectList = projectDataSource.getProjects());
    }


    public LiveData<List<Task>> getTasksList() {
        return this.taskDataSource.getTasks();
    }

    public List<TaskOnUI> getTaskAsTaskOnUiList(List<Task> tasks) {
        List<TaskOnUI> currentTasks = new ArrayList<>();
        if (tasks == null || tasks.size() == 0) return currentTasks;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            Project project = projectList.get(i);
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


