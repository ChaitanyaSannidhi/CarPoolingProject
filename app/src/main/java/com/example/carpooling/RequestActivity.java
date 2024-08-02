package com.example.carpooling;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Request);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_inbox) {
                    Toast.makeText(RequestActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(RequestActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                    return true;
                } else if (id == R.id.navigation_home) {
                    Toast.makeText(RequestActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Toast.makeText(RequestActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    return true;
                }
                return false;
            }
        });
        Button btn2 = findViewById(R.id.Request);
        btn2.setOnClickListener(v2 -> {
            Toast.makeText(RequestActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), Request_recycleActivity.class);
            startActivity(i2);
        });
        dateText = findViewById(R.id.dateText);
        dateText.setOnClickListener(v -> showDatePickerDialog());
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateText.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis()); // Set min date to today

        // Show the date picker dialog
        datePickerDialog.show();
    }
}