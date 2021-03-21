package com.example.todocapp.utils;

import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class TaskListMapperTest {

    private final TaskListMapper mTaskListMapper = new TaskListMapper();

    @Test
    public void getTaskOnUiListFromTaskList_shouldReturnTaskOnUiList_taskListNullCase_shouldReturnListEvenWithNullTaskList() {
        List<Project> projectList = new ArrayList<>();
        List<Task> taskList = null;

        List<TaskOnUI> taskOnUIList = mTaskListMapper.getTaskAsTaskOnUiList(taskList, projectList);

        assertNotNull(taskOnUIList);
    }

    @Test
    public void getTaskOnUiListFromTaskList_shouldReturnTaskOnUiList_taskListEqualZeroCase_shouldReturnListEvenWithEmptyTaskList() {
        List<Project> projectList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();

        List<TaskOnUI> taskOnUIList = mTaskListMapper.getTaskAsTaskOnUiList(taskList, projectList);

        assertEquals("check if we have size condition", 0, taskList.size());
        assertNotNull(taskOnUIList);
    }

    @Test
    public void getTaskFromTaskUi_shouldReturnTask_taskAndProjectIDsEqualsCase_shouldCreateNewElementWithSameRefs() {
        List<Project> projectList = new ArrayList<>();
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(1, 6, "X", Calendar.getInstance().getTime().getTime());
        Project project = new Project(6, "Projet Tartampion", 0xFFEADAD1);
        taskList.add(task);
        projectList.add(project);

        List<TaskOnUI> taskOnUIList = mTaskListMapper.getTaskAsTaskOnUiList(taskList, projectList);
        TaskOnUI taskOnUI = taskOnUIList.get(0);

        assertEquals("check if we have element creation on list", 1, taskOnUIList.size());
        assertNotNull(taskOnUI);
        assertEquals(task.getTaskId(), taskOnUI.getTaskId());
        assertEquals(task.getName(), taskOnUI.getTaskName());
        assertEquals(project.getColor(), taskOnUI.getProjectColor());
        assertEquals(project.getName(), taskOnUI.getProjectName());
    }

    @Test
    public void getTaskFromTaskUi_shouldReturnTask_taskOnUiNameEqualsTaskName_shouldCreateNewElementWithSameNameRefs() {
        List<Project> projectList = new ArrayList<>();
        TaskOnUI taskOnUI = new TaskOnUI(1, 0xFFEADAD1, "Projet Tartampion", "X");

        Task task = mTaskListMapper.getTaskFromTaskUi(taskOnUI, projectList);

        assertNotNull(task);
        assertEquals(task.getName(), taskOnUI.getTaskName());
    }

    @Test
    public void getTaskFromTaskUi_shouldReturnTask_taskOnUiProjectNameEqualsProjectName_shouldCreateNewElementWithSameProjectRefs() {
        List<Project> projectList = new ArrayList<>();
        Project project = new Project(6, "Projet Tartampion", 0xFFEADAD1);
        projectList.add(project);
        TaskOnUI taskOnUI = new TaskOnUI(1, 0xFFEADAD1, "Projet Tartampion", "X");

        Task task = mTaskListMapper.getTaskFromTaskUi(taskOnUI, projectList);

        assertEquals(project.getName(), taskOnUI.getProjectName());
        assertEquals(task.getProjectId(), project.getProjectId());
    }
}