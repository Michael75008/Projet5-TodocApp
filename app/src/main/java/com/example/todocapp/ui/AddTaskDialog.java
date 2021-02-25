package com.example.todocapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.TaskOnUI;

import java.util.List;

import static com.example.todocapp.R.id.project_spinner;
import static com.example.todocapp.R.id.txt_task_name;

public class AddTaskDialog extends AppCompatActivity {

    List<Project> mProjectList;
    TaskOnUI mTask;
    private final Listener2 callback;


    public interface Listener2 {
        void onClickAddTaskButton(TaskOnUI taskOnUI);
    }


    public AddTaskDialog(List<Project> projects, Listener2 callback) {
        this.callback = callback;
        this.mProjectList = projects;
    }

    public void createDialog(Context context) {
        configureAlertDialog(context);
    }

    private void configureAlertDialog(Context context) {
        mTask = new TaskOnUI();
        AlertDialog mDialog;
        Spinner mSpinner;
        EditText taskName;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle("Ajouter une tâche");
        builder.setPositiveButton("Ajouter", null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });
        mDialog = builder.create();
        mDialog.show();
        mSpinner = mDialog.findViewById(project_spinner);
        ArrayAdapter<Project> dataAdapter = new ArrayAdapter<Project>(context, android.R.layout.simple_spinner_item, mProjectList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        taskName = mDialog.findViewById(txt_task_name);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Project taskProject = null;
                if (mSpinner.getSelectedItem() instanceof Project) {
                    taskProject = (Project) mSpinner.getSelectedItem();
                }
                mTask.setProjectName(taskProject.getName());
                mTask.setProjectColor(taskProject.getColor());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
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
            }
        });
    }
}