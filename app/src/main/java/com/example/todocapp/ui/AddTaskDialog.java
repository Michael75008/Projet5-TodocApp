package com.example.todocapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todocapp.R;
import com.example.todocapp.models.Project;
import com.example.todocapp.models.Task;
import com.example.todocapp.todolist.TaskViewHolder;
import com.example.todocapp.todolist.TaskViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.todocapp.R.id.project_spinner;
import static com.example.todocapp.R.id.txt_task_name;

public class AddTaskDialog extends AppCompatActivity {

    AlertDialog mDialog;
    List<Project> mProjectList;
    Context mContext;
    EditText mEditText;
    Spinner mSpinner;
    EditText taskName;
    Task mTask;
    TaskViewModel mTaskViewModel;

    public AddTaskDialog(Context context, List<Project> projects, TaskViewModel taskViewModel) {
        this.mProjectList = projects;
        this.mContext = context;
        this.mTaskViewModel = taskViewModel;
    }

    public void createDialog(Activity activity) {
        this.mTask = new Task();
        configureAlertDialog(activity);
        populateDialogSpinner();
    }

    private void configureAlertDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_task, null);
        builder.setView(view);
        builder.setCancelable(true);
        builder.setTitle("Ajouter une tâche");
        builder.setPositiveButton("Ajouter", null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mEditText = null;
            }
        });
        mDialog = builder.create();
        mDialog.show();
        mSpinner = mDialog.findViewById(project_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getProjectNames(mProjectList));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mEditText = mDialog.findViewById(txt_task_name);
        taskName = mDialog.findViewById(txt_task_name);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mProjectList.size(); i++) {
                    if (mSpinner.getSelectedItem().toString().equals(mProjectList.get(i).getName().toString())) {
                        mTask.setProjectId(mProjectList.get(position).getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button button = mDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                String taskNameTab = taskName.getText().toString();
                // If a name has not been set
                if (taskNameTab.trim().isEmpty()) {
                    mEditText.setError(getString(R.string.empty_task_name));
                }
                else
                mTask.setName(taskName.getText().toString());
                mTask.setCreationTimestamp(Calendar.getInstance().getTimeInMillis());
                createNewTask(mTask);
                mDialog.dismiss();
            }

        });
    }

    public List<String> getProjectNames(List<Project> projects) {
        ArrayList<String> projectNames = new ArrayList<String>();
        for (int i = 0; i < mProjectList.size(); i++) {
            String projectName = mProjectList.get(i).getName();
            projectNames.add(projectName);
        }
        return projectNames;
    }


    private void populateDialogSpinner() {
    }

    public void onItemSelected(View view, int position, long id) {
    }

    private void createNewTask(Task task) {
        mTaskViewModel.createTask(task);
    }

}



