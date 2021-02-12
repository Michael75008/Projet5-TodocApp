package com.example.todocapp.todolist;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.SortMethod;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel implements TaskAdapter.Listener {

    private final TaskDataRepository taskDataSource;
    private final ProjectDataRepository projectDataSource;
    private final TaskListMapper mTaskListMapper;
    private final Executor executor;
    SortMethod mSortMethod = SortMethod.NONE;

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor, TaskListMapper taskListMapper) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
        this.mTaskListMapper = taskListMapper;
    }

    @Nullable
    private List<Project> projectList;

    @Nullable
    private LiveData<List<Task>> tasksList;


    public void init() {
        executor.execute(() -> tasksList = taskDataSource.getTasks());
        executor.execute(() -> projectList = projectDataSource.getProjects());
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

    public List<Project> getProjectsList() {
        return projectList;
    }

    public void update (int id) {
        if (id == R.id.filter_alphabetical) {
            mSortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            mSortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            mSortMethod = SortMethod.RECENT_FIRST;
        }
        updateTaskList();
    }

    public List<TaskOnUI> getTasksOnUi(List<Task> tasks, TaskAdapter adapter) {
        switch (mSortMethod) {
            case ALPHABETICAL:
                Collections.sort(tasks, new Task.TaskAZComparator());
                break;
            case ALPHABETICAL_INVERTED:
                Collections.sort(tasks, new Task.TaskZAComparator());
                break;
            case RECENT_FIRST:
                Collections.sort(tasks, new Task.TaskRecentComparator());
                break;
            case OLD_FIRST:
                Collections.sort(tasks, new Task.TaskOldComparator());
                break;
        }
        adapter.updateData(mTaskListMapper.getTaskAsTaskOnUiList(tasks, projectList));
        //trier la liste avec sortmethod, puis update (int, adapter)
        return mTaskListMapper.getTaskAsTaskOnUiList(tasks,projectList);
}

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(int taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }

    @Override
    public void onClickDeleteButton(TaskOnUI taskOnUI) {
        deleteTask(taskOnUI.getTaskId());
    }
}