package com.example.carpooling;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class OfferActivity extends AppCompatActivity {

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
        dateText.setOnClickListener(v -> showDatePickerDialog());

        // Button to save ride offer
        Button saveButton = findViewById(R.id.offer);
        saveButton.setOnClickListener(v -> saveRideOffer());

        // Button to navigate to OfferFragment
        Button offerButton = findViewById(R.id.offer); // Ensure this ID matches your XML layout
        offerButton.setOnClickListener(v -> navigateToOfferFragment());
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

    private void navigateToOfferFragment() {
        Fragment offerFragment = new OfferFragment(); // Make sure you have this fragment class
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, offerFragment); // Ensure this ID matches your XML layout
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
