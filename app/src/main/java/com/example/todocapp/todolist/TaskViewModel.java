package com.example.todocapp.todolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.SortMethod;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.ui.AddTaskDialog;
import com.example.todocapp.utils.TaskListMapper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel implements TaskAdapter.Listener, AddTaskDialog.Listener2 {

    // For init

    private TaskDataRepository taskDataSource;
    private ProjectDataRepository projectDataSource;
    private TaskListMapper mTaskListMapper;
    private Executor executor;

    private List<Project> projectList;
    private LiveData<List<Task>> tasksList;

    SortMethod mSortMethod = SortMethod.NONE;

    // Constructor

    public TaskViewModel(TaskDataRepository taskDataSource, ProjectDataRepository projectDataSource, Executor executor, TaskListMapper taskListMapper) {
        this.taskDataSource = taskDataSource;
        this.projectDataSource = projectDataSource;
        this.executor = executor;
        this.mTaskListMapper = taskListMapper;
    }

    // Initialisation of task list and project list

    public LiveData<List<Task>> initLists() {
        executor.execute(() -> projectList = projectDataSource.getProjects());
        if (tasksList == null) {
            tasksList = taskDataSource.getTasks();
        }
        return tasksList;
    }

    // Display sorter with id ref

    public void displaySorter(int id, TaskAdapter adapter) {
        if (id == R.id.filter_alphabetical) {
            mSortMethod = SortMethod.ALPHABETICAL;
        } else if (id == R.id.filter_alphabetical_inverted) {
            mSortMethod = SortMethod.ALPHABETICAL_INVERTED;
        } else if (id == R.id.filter_oldest_first) {
            mSortMethod = SortMethod.OLD_FIRST;
        } else if (id == R.id.filter_recent_first) {
            mSortMethod = SortMethod.RECENT_FIRST;
        }
        getTasksOnUi(tasksList.getValue(), adapter);
    }

    // Sort methods for each case

    public void sortTasks(List<Task> tasks) {
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
    }

    // Get method for project list

    public List<Project> getProjectsList() {
        return projectList;
    }

    // Get method for TaskOnUi list

    public List<TaskOnUI> getTasksOnUi(List<Task> tasks, TaskAdapter adapter) {
        sortTasks(tasks);
        adapter.updateData(mTaskListMapper.getTaskAsTaskOnUiList(tasks, projectList));
        return mTaskListMapper.getTaskAsTaskOnUiList(tasks, projectList);
    }

    // Task's methods for create and delete task

    public void createTask(Task task) {
        executor.execute(() -> taskDataSource.createTask(task));
    }

    public void deleteTask(int taskId) {
        executor.execute(() -> taskDataSource.deleteTask(taskId));
    }

    // OnClick implementations

    @Override
    public void onClickDeleteButton(TaskOnUI taskOnUI) {
        deleteTask(taskOnUI.getTaskId());
    }

    @Override
    public void onClickAddTaskButton(TaskOnUI taskOnUI) {
        createTask(mTaskListMapper.getTaskFromTaskUi(taskOnUI, projectList));
    }
}