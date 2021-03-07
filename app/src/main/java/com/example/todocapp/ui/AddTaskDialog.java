package com.example.todocapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.TaskOnUI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.AdapterView.OnItemSelectedListener;
import static com.example.todocapp.R.id.project_spinner;
import static com.example.todocapp.R.id.txt_task_name;

public class AddTaskDialog extends AppCompatActivity {

    // Callback interface for add button

    public interface Listener2 {
        void onClickAddTaskButton(TaskOnUI taskOnUI);
    }

    // For init

    private final Listener2 callback;
    List<Project> mProjectList;

    @BindView(project_spinner)
    Spinner mSpinner;
    @BindView(txt_task_name)
    EditText taskName;

    // Construtor

    public AddTaskDialog(List<Project> projects, Listener2 callback) {
        this.callback = callback;
        this.mProjectList = projects;
    }

    // Public method to create dialog

    public void createDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        ButterKnife.bind(this, view);
        configureAlertDialog(context, view);
    }

    // Private method to configure its view and actions

    private void configureAlertDialog(Context context, View view) {
        TaskOnUI mTask = new TaskOnUI();
        AlertDialog mDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle(context.getString(R.string.add_task));
        builder.setPositiveButton(context.getString(R.string.add_it), null);
        mDialog = builder.create();
        mDialog.show();
        configureSpinner(context, mTask);
        mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String taskNameTab = taskName.getText().toString();
            // If a name has not been set
            if (taskNameTab.trim().isEmpty()) {
                taskName.setError(context.getString(R.string.empty_task_name));
            } else {
                mTask.setTaskName(taskName.getText().toString());
                if (callback != null)
                    callback.onClickAddTaskButton(mTask);
                mDialog.dismiss();
            }
        });
    }

    // Spinner configurations

    private void configureSpinner(Context context, TaskOnUI task) {
        ArrayAdapter<Project> dataAdapter = new ArrayAdapter<Project>(context, android.R.layout.simple_spinner_item, mProjectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project taskProject = null;
                if (mSpinner.getSelectedItem() instanceof Project) {
                    taskProject = (Project) mSpinner.getSelectedItem();
                }
                task.setProjectName(taskProject.getName());
                task.setProjectColor(taskProject.getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}