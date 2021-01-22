package com.example.todocapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.injections.ViewModelFactory;
import com.example.todocapp.models.Task;
import com.example.todocapp.todolist.TaskAdapter;
import com.example.todocapp.todolist.TaskViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    public void onClickDeleteButton(int position) {
        this.deleteTask(this.adapter.getTask(position));
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.taskViewModel = new ViewModelProvider(this, mViewModelFactory).get(TaskViewModel.class);
        this.taskViewModel.init();
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