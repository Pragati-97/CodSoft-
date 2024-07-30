package com.sakshi13.getitdone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Screen2Activity extends AppCompatActivity {

    private List<Task> taskList = new ArrayList<>();
    private LinearLayout taskContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        // Initialize UI elements
        TextView headerTextView = findViewById(R.id.headerTextView);
        EditText searchEditText = findViewById(R.id.searchEditText);
        Button addTaskButton = findViewById(R.id.addTaskButton);
        taskContainer = findViewById(R.id.taskContainer);

        // Set header text
        headerTextView.setText("Hello Pragati!");

        // Add Task button click listener
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Screen2Activity.this, Screen3Activity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Search button click listener
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString().trim();
                filterTasks(query);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Task newTask = (Task) data.getSerializableExtra("newTask");
            if (newTask != null) {
                taskList.add(newTask);
                displayTasks(taskList);
            }
        }
    }

    private void filterTasks(String query) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : taskList) {
            if (task.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredTasks.add(task);
            }
        }
        displayTasks(filteredTasks);
    }

    private void displayTasks(List<Task> tasks) {
        taskContainer.removeAllViews();
        for (Task task : tasks) {
            View taskView = getLayoutInflater().inflate(R.layout.task_item, taskContainer, false);
            TextView titleTextView = taskView.findViewById(R.id.taskTitleTextView);
            TextView descriptionTextView = taskView.findViewById(R.id.taskDescriptionTextView);
            TextView deadlineTextView = taskView.findViewById(R.id.taskDeadlineTextView);
            CheckBox checkBox = taskView.findViewById(R.id.taskCheckBox);

            titleTextView.setText(task.getTitle());
            descriptionTextView.setText(task.getDescription());
            deadlineTextView.setText(task.getDeadline());
            checkBox.setChecked(task.isCompleted());

            taskContainer.addView(taskView);
        }
    }
}
