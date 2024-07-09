package com.example.carpooling;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

public class OfferActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private TextView dateTextView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_offer);
        Spinner seatSpinner = findViewById(R.id.seatSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        seatSpinner.setAdapter(adapter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize TextView
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calendarView.getVisibility() == View.GONE) {
                    calendarView.setVisibility(View.VISIBLE);
                } else {
                    calendarView.setVisibility(View.GONE);
                }
            }
        });

        // Initialize CalendarView
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                dateTextView.setText(selectedDate);
                calendarView.setVisibility(View.GONE);  // Hide the calendar after selecting a date
            }
        });
    }

}
//hiiii