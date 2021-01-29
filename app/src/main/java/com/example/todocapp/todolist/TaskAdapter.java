package com.example.todocapp.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.models.TaskOnUI;
import com.example.todocapp.utils.LiveDataTestUtil;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    //CALLBACK
    public interface Listener {
        void onClickDeleteButton(int position);
    }

    private final Listener callback;
    /**
     * The list of tasks the adapter deals with
     */
    private List<Task> tasks;
    private List<TaskOnUI> tasksOnUi;
    private List<Project> projects;

    public TaskAdapter(Listener callback) {
        this.callback = callback;
        this.tasksOnUi = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_task, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int position) {
        taskViewHolder.updateWithTask(this.tasksOnUi.get(position), this.callback);
    }

    @Override
    public int getItemCount() {
        return this.tasksOnUi.size();
    }

    public Task getTask(int position){
        return this.tasks.get(position);
    }

    public void updateData(List<TaskOnUI>tasks){
        this.tasksOnUi = tasks;
        this.notifyDataSetChanged();
    }
}
