package com.example.todocapp.todolist;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.todocapp.R;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.SortMethod;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.repositories.ProjectDataRepository;
import com.example.todocapp.repositories.TaskDataRepository;
import com.example.todocapp.utils.TaskListMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

import static org.junit.Assert.assertSame;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class TaskViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskViewModel sut;

    private Executor executor;

    @Mock
    private ProjectDataRepository mProjectDataRepository;

    @Mock
    private TaskDataRepository mTaskDataRepository;

    @Mock
    private TaskListMapper mTaskListMapper;

    @Before
    public void setUp() {
        executor = Injection.provideExecutor();
        MockitoAnnotations.initMocks(this);
        sut = new TaskViewModel(mTaskDataRepository, mProjectDataRepository, executor, mTaskListMapper);
    }

    @Test
    public void initLists() {
        executor.execute(() -> {
            List<Task> tasks = new ArrayList<>();
            List<Project> projects = new ArrayList<>();
            Project project = new Project((int) 1L, "Projet Tartampion", 0xFFEADAD1);
            Task task1 = new Task(1, (int) 3L, "C", mDate("01-01-2021 12:00:00").getTime());
            Task task2 = new Task(1, (int) 3L, "C", mDate("01-01-2021 12:00:00").getTime());
            MutableLiveData<List<Task>> fakeLiveData = new MutableLiveData<List<Task>>() {
            };
            tasks.add(task1);
            tasks.add(task2);
            fakeLiveData.setValue(tasks);
            projects.add(project);


            given(mProjectDataRepository.getProjects()).willReturn(projects);
            given(mTaskDataRepository.getTasks()).willReturn(fakeLiveData);

            sut.initLists();


            verify(mTaskDataRepository, times(1)).getTasks();
            verify(mProjectDataRepository, times(1)).getProjects();
        });
    }

    @Test
    public void displaySorter_shouldDiplaySortMethodDependingOnFilterId() {
        int id = R.id.filter_alphabetical_inverted;

        sut.displaySorter(id);

        assertSame(sut.mSortMethod, SortMethod.ALPHABETICAL_INVERTED);
    }

    @Test
    public void sortTasks_shouldSortTasksWhenMethodCalledDependingOnSortChoice() {
        List<Task> tasks = new ArrayList<>();
        Task task1 = new Task(1, (int) 1L, "A", mDate("01-01-2021 12:00:00").getTime());
        Task task2 = new Task(2, (int) 1L, "B", mDate("02-01-2021 12:00:00").getTime());
        tasks.add(0, task2);
        tasks.add(1, task1);
        sut.mSortMethod = SortMethod.ALPHABETICAL;

        sut.sortTasks(tasks);

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
    }

    @Test
    public void getProjectsList() {
        executor.execute(() -> {
            List<Project> projects = new ArrayList<>();
            Project project = new Project((int) 1L, "Projet Tartampion", 0xFFEADAD1);
            projects.add(project);
            given(mProjectDataRepository.getProjects()).willReturn(projects);
            MutableLiveData<List<Task>> fakeLiveData = new MutableLiveData<List<Task>>() {
            };
            given(mTaskDataRepository.getTasks()).willReturn(fakeLiveData);
            sut.initLists();

            List<Project> result = sut.getProjectsList();

            verify(mProjectDataRepository, times(1)).getProjects();
            assertSame(result, projects);
        });
    }

    @Test
    public void deleteTask() {
        executor.execute(() -> {
            Task task = new Task(1, 2, "Demo task name", new Date().getTime());

            sut.deleteTask(task.getTaskId());

            verify(mTaskDataRepository, times(1)).deleteTask(task.getTaskId());
        });
    }

    @Test
    public void createTask() {
        executor.execute(() -> {
            Task task = new Task(1, 2, "Demo task name", new Date().getTime());

            sut.createTask(task);

            verify(mTaskDataRepository, times(1)).createTask(task);
        });

    }

    @Test
    public void onClickDeleteButton() {
        executor.execute(() -> {
            TaskOnUI taskOnUI = new TaskOnUI(1, 0xFFEADAD1, "Projet Tartampion", "Demo task");

            sut.onClickDeleteButton(taskOnUI);

            verify(mTaskDataRepository, times(1)).deleteTask(taskOnUI.getTaskId());
        });

    }

    @Test
    public void onClickAddTaskButton() {
        executor.execute(() -> {
            List<Project> projects = new ArrayList<>();
            TaskOnUI taskOnUI = new TaskOnUI(1, 0xFFEADAD1, "Projet Tartampion", "Demo task on Ui name");

            sut.onClickAddTaskButton(taskOnUI);

            verify(mTaskDataRepository, times(1)).createTask(mTaskListMapper.getTaskFromTaskUi(taskOnUI, projects));
        });
    }

    private static Date mDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.FRANCE);
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }
}