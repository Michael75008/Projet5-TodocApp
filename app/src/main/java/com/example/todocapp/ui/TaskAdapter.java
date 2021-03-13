package com.example.todocapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.models.TaskOnUI;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    // CallBack Interface for delete button

    public interface Listener {
        void onClickDeleteButton(TaskOnUI taskOnUI);
    }

    // For init

    private final Listener callback;
    private List<TaskOnUI> tasksOnUi;

    // Constructor

    public TaskAdapter(Listener callback) {
        this.callback = callback;
        this.tasksOnUi = new ArrayList<>();
    }

    // OnCreate method to define view

    @NonNull
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(view);
    }

    // OnBind method to join data to view

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.updateWithTask(this.tasksOnUi.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.tasksOnUi.size();
    }

    // Updates list of TaskOnUi's when change occurs

    public void updateData(List<TaskOnUI> tasks) {
        this.tasksOnUi = tasks;
        this.notifyDataSetChanged();
    }
}
