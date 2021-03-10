package com.example.todocapp.utils;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.database.dao.ProjectDatabase;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.example.todocapp.database.dao.ProjectDatabase.prepopulateDataBase;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TaskListMapperTest {

    // FOR DATA
    private ProjectDatabase database;
    // CLASS CALL
    private final TaskListMapper mTaskListMapper = new TaskListMapper();


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() {
        this.database =
                Room.inMemoryDatabaseBuilder(getApplicationContext(), ProjectDatabase.class)
                        .allowMainThreadQueries()
                        .addCallback(prepopulateDataBase())
                        .build();
    }

    @Test
    public void getTaskAsTaskOnUiListTest_shouldConvertTaskListOnTaskOnUiListBasedOnProjectAndTaskLists() throws InterruptedException {
        // Get Project list from database
        List<Project> projectList = this.database.projectDao().getProjects();
        // Get our list of tasks from DB
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasks());

        List<TaskOnUI> taskOnUIList = mTaskListMapper.getTaskAsTaskOnUiList(taskList, projectList);
        // Check we have the same size for both lists, confirms that we update task list into new format
        assertEquals(taskOnUIList.size(), taskList.size());
        // Check we find the same task names
        assertEquals(taskOnUIList.get(0).getTaskName(), taskList.get(0).getName());
        assertEquals(taskOnUIList.get(1).getTaskName(), taskList.get(1).getName());
        assertEquals(taskOnUIList.get(2).getTaskName(), taskList.get(2).getName());
        // Check we find the same task ids
        assertEquals(taskOnUIList.get(0).getTaskId(), taskList.get(0).getTaskId());
        assertEquals(taskOnUIList.get(1).getTaskId(), taskList.get(1).getTaskId());
        assertEquals(taskOnUIList.get(2).getTaskId(), taskList.get(2).getTaskId());
        // Check we find the same project names and project colors
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            for (int i2 = 0; i2 < projectList.size(); i2++) {
                Project project = projectList.get(i2);
                for (int i3 = 0; i3 < taskOnUIList.size(); i3++) {
                    TaskOnUI taskOnUI = taskOnUIList.get(i);
                    if (project.getProjectId() == task.getProjectId()) {
                        assertEquals(project.getColor(), taskOnUI.getProjectColor());
                        assertEquals(project.getName(), taskOnUI.getProjectName());
                    }
                }
            }
        }
    }

    @Test
    public void getTaskFromTaskUi_shouldConvertTaskOnTaskOnUiBasedOnProjectListAndTaskOnUi() {
        // Get Project list from database
        List<Project> projectList = this.database.projectDao().getProjects();
        // Create a new TaskOnUi with projectList reference at index 1
        TaskOnUI taskOnUI = new TaskOnUI(4, projectList.get(1).getColor(), projectList.get(1).getName(), "taskNameDemo");
        // Create a new Task with projectList and TaskOnUi references
        Task task = mTaskListMapper.getTaskFromTaskUi(taskOnUI, projectList);
        // Check if we find the same name in task and taskOnUi
        assertEquals(task.getName(), taskOnUI.getTaskName());
        // Check if we fin the same projectId in Task and Project at index 1
        assertEquals(task.getProjectId(), projectList.get(1).getProjectId());
    }

    @After
    public void closeDb() {
        database.close();
    }
}