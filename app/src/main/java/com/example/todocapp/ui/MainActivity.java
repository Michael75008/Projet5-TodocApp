package com.example.todocapp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.injections.ViewModelFactory;
import com.example.todocapp.models.Task;
import com.example.todocapp.todolist.TaskViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_tasks)
    RecyclerView recyclerView;
    @BindView(R.id.lbl_no_task)
    TextView mLblNoTasks;

    // For init

    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.configureViewModel();
        this.configureRecyclerView();
    }

    // Menu configuration

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        taskViewModel.displaySorter(item.getItemId());
        taskViewModel.getTasksOnUi(taskViewModel.initLists().getValue(), adapter);
        return super.onOptionsItemSelected(item);
    }

    // ViewModel configuration

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.taskViewModel = new ViewModelProvider(this, mViewModelFactory).get(TaskViewModel.class);
        taskViewModel.initLists().observe(this, this::taskObserver);
    }

    private void taskObserver(List<Task> tasks) {
        setVisibility(tasks.size() == 0);
        adapter.updateData(taskViewModel.getTasksOnUi(tasks, adapter));
    }

    // RecyclerView configuration

    private void configureRecyclerView() {
        this.adapter = new TaskAdapter(taskViewModel);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Empty list icon visibility throw boolean

    private void setVisibility(boolean isVisible) {
        if (isVisible) {
            mLblNoTasks.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            mLblNoTasks.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.fab_add_task)
    public void onFabClick() {
        new AddTaskDialog(taskViewModel.getProjectsList(), taskViewModel).createDialog(this);
    }
}