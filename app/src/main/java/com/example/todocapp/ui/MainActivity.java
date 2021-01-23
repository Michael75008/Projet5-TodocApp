package com.example.todocapp.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.injections.ViewModelFactory;
import com.example.todocapp.models.Task;
import com.example.todocapp.todolist.TaskAdapter;
import com.example.todocapp.todolist.TaskViewModel;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TaskAdapter.Listener {

    @BindView(R.id.list_tasks)
    RecyclerView recyclerView;

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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @OnClick(R.id.fab_add_task)
    public void onFabClick(){
        new AddTaskDialog().createDialog();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_alphabetical:
            case R.id.filter_alphabetical_inverted:
            case R.id.filter_oldest_first:
            case R.id.filter_recent_first:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClickDeleteButton(int position) {
        this.deleteTask(this.adapter.getTask(position));
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.taskViewModel = new ViewModelProvider(this, mViewModelFactory).get(TaskViewModel.class);
        this.taskViewModel.init();
        taskViewModel.getTasksList().observe(this, this::taskObserver);
    }

    private void taskObserver(List<Task > tasks){

        adapter.updateData(tasks);
    }

    private void configureRecyclerView() {
        this.adapter = new TaskAdapter(this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void deleteTask(Task task){
        this.taskViewModel.deleteTask(task.getTaskId());
    }
}