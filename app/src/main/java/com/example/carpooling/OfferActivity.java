package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OfferActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText startPointEditText, destinationEditText, intermediatePointsEditText;
    private TextView dateText;
    private Spinner seatSpinner;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize Spinner for seat selection
        seatSpinner = findViewById(R.id.seatSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.seat_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seatSpinner.setAdapter(adapter);

        // Initialize EditText fields
        startPointEditText = findViewById(R.id.editTextStartPoint);
        destinationEditText = findViewById(R.id.editTextDestinationPoint);
        intermediatePointsEditText = findViewById(R.id.editTextIntermediateDropPoints);

        // Adjust window insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bottom navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.offer);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_inbox) {
                Toast.makeText(OfferActivity.this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                return true;
            } else if (id == R.id.navigation_rides) {
                Toast.makeText(OfferActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                return true;
            } else if (id == R.id.navigation_home) {
                Toast.makeText(OfferActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                return true;
            } else if (id == R.id.navigation_profile) {
                Toast.makeText(OfferActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileMenuActivity.class));
                return true;
            }
            return false;
        });

        // Date selection logic
        dateText = findViewById(R.id.dateText);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE); // Ensure calendar is initially hidden
        dateText.setOnClickListener(v -> toggleCalendarViewVisibility());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateText.setText(selectedDate);
            calendarView.setVisibility(View.GONE);
        });

        // Button to save ride offer
        Button saveButton = findViewById(R.id.offer);
        saveButton.setOnClickListener(v -> saveRideOffer());
    }

    private void toggleCalendarViewVisibility() {
        if (calendarView.getVisibility() == View.GONE) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.GONE);
        }
    }

    private void saveRideOffer() {
        String selectedDate = dateText.getText().toString();
        String selectedSeats = seatSpinner.getSelectedItem().toString();
        String startPoint = startPointEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        String intermediatePoints = intermediatePointsEditText.getText().toString().trim();

        // Validate input
        if (startPoint.isEmpty() || destination.isEmpty() || selectedDate.isEmpty() || selectedSeats.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new ride offer document in "rides" collection
        Map<String, Object> rideOffer = new HashMap<>();
        rideOffer.put("date", selectedDate);
        rideOffer.put("seats", selectedSeats);
        rideOffer.put("start_point", startPoint);
        rideOffer.put("destination", destination);
        rideOffer.put("intermediate_points", intermediatePoints);

        // Add a new document with a generated ID
        db.collection("rides")
                .add(rideOffer)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(OfferActivity.this, "Ride offer saved successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, navigate to another activity or clear fields
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(OfferActivity.this, "Error saving ride offer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void clearFields() {
        startPointEditText.setText("");
        destinationEditText.setText("");
        intermediatePointsEditText.setText("");
        dateText.setText("");
        seatSpinner.setSelection(0);
    }
}
