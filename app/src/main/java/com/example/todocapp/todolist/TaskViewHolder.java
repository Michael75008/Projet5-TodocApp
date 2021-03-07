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

    // For init

    private WeakReference<TaskAdapter.Listener> callbackWeakRef;
    public TaskOnUI sTaskOnUI;

    // BindViews

    @BindView(R.id.img_delete)
    AppCompatImageButton imgDelete;
    @BindView(R.id.lbl_project_name)
    TextView lblProjectName;
    @BindView(R.id.lbl_task_name)
    TextView lblTaskName;
    @BindView(R.id.img_project)
    AppCompatImageView imgProject;

    // Constructor

    public TaskViewHolder(View taskView) {
        super(taskView);
        ButterKnife.bind(this, taskView);
    }

    // Update method to fix view of taskOnUi and WeakRef init

    @SuppressLint("RestrictedApi")
    public void updateWithTask(TaskOnUI task, TaskAdapter.Listener callback) {
        sTaskOnUI = task;
        this.imgDelete.setOnClickListener(this);
        this.lblTaskName.setText(task.getTaskName());
        this.imgProject.setSupportImageTintList(ColorStateList.valueOf(task.getProjectColor()));
        this.lblProjectName.setText(task.getProjectName());
        this.callbackWeakRef = new WeakReference<>(callback);
    }

    // OnClick delete button where we call our callback throw our WeakRef

    @Override
    public void onClick(View v) {
        TaskAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null)
            callback.onClickDeleteButton(sTaskOnUI);
    }
}

