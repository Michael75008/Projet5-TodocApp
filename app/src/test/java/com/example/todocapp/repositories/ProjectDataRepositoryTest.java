package com.example.todocapp.repositories;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.todocapp.database.dao.ProjectDao;
import com.example.todocapp.models.Project;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ProjectDataRepositoryTest {

    @Mock
    private ProjectDao projectDao;

    @Mock
    private ProjectDataRepository sut;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sut = new ProjectDataRepository(projectDao);
    }

    @Test
    public void getProjectsTest() {
        List<Project> projects = new ArrayList<>();
        Project project = new Project(1, "Projet Tartampion", 0xFFEADAD1);
        projects.add(project);
        when(projectDao.getProjects()).thenReturn(projects);

        List<Project> result = sut.getProjects();

        assertNotEquals(0, result.size());
    }
}