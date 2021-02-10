package com.example.todocapp.todolist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final Executor executor;
    TaskListMapper mTaskListMapper;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor, TaskListMapper taskListMapper) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    @Nullable
    private List<Project> projectList;

    @Nullable
    private LiveData<List<Task>> tasksList;

    private List<Task> mTasks;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
    }

    public void init() {
        executor.execute(() -> tasksList = taskDataSource.getTasks());
        executor.execute(() -> projectList = projectDataSource.getProjects());
        mTaskListMapper = new TaskListMapper();
    }

    public LiveData<List<Task>> updateTaskList() {
        return taskDataSource.getTasks();
    }


    public LiveData<List<Task>> getTasksList() {
        if (tasksList == null) {
            tasksList = updateTaskList();
        }

        return tasksList;
    }

    public LiveData<List<Task>> getTasksByDesc() {
        return  tasksList = taskDataSource.getTaskssByDesc();
    }

    public LiveData<List<Task>> getTasksByAsc() {
        return tasksList = taskDataSource.getTasksByAsc();
    }
    public LiveData<List<Task>> getTaskByCreationTimeAsc() {
        return tasksList = taskDataSource.getTasksByCreationTimeAsc();
    }

    public LiveData<List<Task>> getTasksByCreationTimeDesc() {
        return tasksList = taskDataSource.getTasksByCreationTimeDesc();
    }


    public List<TaskOnUI> getTasksOnUi(List<Task> tasks) {
        return mTaskListMapper.getTaskAsTaskOnUiList(tasks, projectList);
    }

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(int taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }
}