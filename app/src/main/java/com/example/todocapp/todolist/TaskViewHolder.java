package com.example.todocapp.todolist;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todocapp.R;
import com.example.todocapp.models.TaskOnUI;

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

    @SuppressLint("RestrictedApi")
    public void updateWithTask(TaskOnUI task, TaskAdapter.Listener callback) {
        this.imgDelete.setOnClickListener(this);
        this.lblTaskName.setText(task.getTaskName());
        this.imgProject.setSupportImageTintList(ColorStateList.valueOf(task.getProjectColor()));
        this.lblProjectName.setText(task.getProjectName());
        this.callbackWeakRef = new WeakReference<>(callback);
    }

    @Override
    public void onClick(View view) {
        TaskAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null)
            callback.onClickDeleteButton(getAdapterPosition());
    }
}
