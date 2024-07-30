package com.sakshi13.getitdone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Screen3Activity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private TextView deadlineTextView;
    private Button addTaskButton;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        // Initialize UI elements
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        deadlineTextView = findViewById(R.id.deadlineTextView);
        addTaskButton = findViewById(R.id.addTaskButton);

        calendar = Calendar.getInstance();

        // Set default deadline to current date and time
        updateDeadlineTextView();

        deadlineTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePicker();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    private void showDateTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimePicker();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateDeadlineTextView();
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void updateDeadlineTextView() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Months are 0-based
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String deadline = String.format("%02d/%02d/%d %02d:%02d", day, month, year, hour, minute);
        deadlineTextView.setText(deadline);
    }

    private void addTask() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String deadline = deadlineTextView.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
            // Show an error message or toast
            return;
        }

        Task newTask = new Task(title, description, deadline, false);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("newTask", newTask);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
