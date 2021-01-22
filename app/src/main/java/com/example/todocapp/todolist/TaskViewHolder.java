package com.example.todocapp.todolist;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private WeakReference<TaskAdapter.Listener> callbackWeakRef;

    @BindView(R.id.img_delete)
    AppCompatImageButton imgDelete;
    @BindView(R.id.lbl_project_name)
    TextView lblProjectName;
    @BindView(R.id.lbl_task_name)
    TextView lblTaskName;
    @BindView(R.id.img_project)
    AppCompatImageView imgProject;

    public TaskViewHolder(View taskView) {
        super(taskView);
        ButterKnife.bind(this, taskView);
    }

    public void updateWithTask(Task task, TaskAdapter.Listener callback) {
        this.imgDelete.setOnClickListener(this);
        this.lblTaskName.setText(task.getName());
       /* this.imgProject.setBackgroundResource(project.getColor());
        this.lblProjectName.setText(project.getName());*/
        this.callbackWeakRef = new WeakReference<TaskAdapter.Listener>(callback);
    }

    @Override
    public void onClick(View view) {
        TaskAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null)
            callback.onClickDeleteButton(getAdapterPosition());
    }
}
