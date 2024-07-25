package com.example.carpooling;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PersonalActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private EditText editTextName, editTextEmail, editTextPhone;
    private Spinner spinnerCountryCode;
    private Button buttonSave;
    private TextView dateTextView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String selectedDate;
    private static final String TAG = "PersonalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personalhead), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextName = findViewById(R.id.editpersname);
        editTextEmail = findViewById(R.id.enteremailid);
        editTextPhone = findViewById(R.id.editTextPhone);
        spinnerCountryCode = findViewById(R.id.spinnerCountryCode);
        buttonSave = findViewById(R.id.savebutton);
        dateTextView = findViewById(R.id.dateTextView);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE); // Ensure calendar is initially hidden

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_codes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        dateTextView.setOnClickListener(v -> toggleCalendarViewVisibility());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            dateTextView.setText(selectedDate);
            calendarView.setVisibility(View.GONE);
        });

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String countryCode = spinnerCountryCode.getSelectedItem().toString();
            String dob = dateTextView.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(dob)) {
                saveProfile(name, email, phone, countryCode, dob);
            } else {
                Toast.makeText(PersonalActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfile(String name, String email, String phone, String countryCode, String dob) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DocumentReference userDocRef = db.collection("personal_details").document(userId);
            Map<String, Object> profile = new HashMap<>();
            profile.put("name", name);
            profile.put("email", email);
            profile.put("phone", countryCode + " " + phone);
            profile.put("date_of_birth", dob);

            userDocRef.set(profile)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PersonalActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "Error updating profile", task.getException());
                            Toast.makeText(PersonalActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleCalendarViewVisibility() {
        if (calendarView.getVisibility() == View.GONE) {
            calendarView.setVisibility(View.VISIBLE);
        } else {
            calendarView.setVisibility(View.GONE);
        }
    }

    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                return i;
            }
        }
        return 0;
    }
}
