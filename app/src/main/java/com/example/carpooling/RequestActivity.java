package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RequestActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request);

        // Initialize Spinner for seat selection
        Spinner seatSpinner = findViewById(R.id.seatSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatSpinner.setAdapter(adapter);

        // Adjust window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set onClick listeners for navigation buttons
        ImageView btn1 = findViewById(R.id.homebutton);
        btn1.setOnClickListener(v1 -> {
            Toast.makeText(RequestActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i2);
        });

        ImageView btn2 = findViewById(R.id.profilebutton);
        btn2.setOnClickListener(v2 -> {
            Toast.makeText(RequestActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i3 = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i3);
        });

        ImageView btn4 = findViewById(R.id.inboxbutton);
        btn4.setOnClickListener(v3 -> {
            Toast.makeText(RequestActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i4 = new Intent(getApplicationContext(), InboxActivity.class);
            startActivity(i4);
        });

        // Date selection logic
        dateText = findViewById(R.id.dateText);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE); // Ensure calendar is initially hidden
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarView.setVisibility(View.VISIBLE);
            }
        });

        dateText.setOnClickListener(v -> toggleCalendarViewVisibility());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateText.setText(selectedDate);
            calendarView.setVisibility(View.GONE);
        });
    }

    private void toggleCalendarViewVisibility() {
        if (calendarView.getVisibility() == View.GONE) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.GONE);
        }
    }
}
//spark