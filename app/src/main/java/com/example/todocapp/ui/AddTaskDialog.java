package com.example.todocapp.ui;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.todocapp.R;
import com.example.todocapp.injections.Injection;
import com.example.todocapp.injections.ViewModelFactory;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.todolist.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class AddTaskDialog {

    @BindView(R.id.txt_task_name)
    EditText mEditText;

    AlertDialog mDialog;
    Spinner mSpinner;
    List<Project> mProjectList;
    ArrayAdapter<Project> dataAdapter;

    public AddTaskDialog(List<Project> projects) {
        this.mProjectList = projects;
    }


    public void createDialog(Activity activity) {
        ButterKnife.bind(activity);
        configureAlertDialog(activity);
    }


    private void configureAlertDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_add_task, null));
        builder.setCancelable(true);
        builder.setTitle("Ajouter une tâche");
        builder.setPositiveButton("Ajouter", null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mSpinner = null;
                mEditText = null;
            }
        });
        mDialog = builder.create();
        mDialog.show();
        Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewTask(new Task());
            }
        });    }



    @OnItemClick(R.id.project_spinner)
    public void onItemClicked(View view, int position, long id) {
    }

    private void createNewTask(Task task) {
    }
}

