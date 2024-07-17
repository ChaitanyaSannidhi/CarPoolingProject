package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.HashMap;

public class PersonalActivity extends AppCompatActivity {
    private CalendarView calendarView;
    private EditText editTextName, editTextEmail, editTextPhone;
    private Spinner spinnerCountryCode;
    private Button buttonSave, buttonMenuBar;
    private TextView dateTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        buttonSave = findViewById(R.id.button2);
        buttonMenuBar = findViewById(R.id.to_menu); // Initialize the menu_bar Button
        dateTextView = findViewById(R.id.dateTextView);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_codes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountryCode.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        // Set the onClickListener for the menu_bar Button
        buttonMenuBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalActivity.this, ProfileMenuActivity.class);
                startActivity(intent);
            }
        });

        loadUserProfile();
    }

    private void saveProfile() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String countryCode = spinnerCountryCode.getSelectedItem().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            Map<String, Object> profile = new HashMap<>();
            profile.put("name", name);
            profile.put("email", email);
            profile.put("phone", countryCode + " " + phone);

            mDatabase.child("users").child(userId).updateChildren(profile)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PersonalActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PersonalActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void loadUserProfile() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            mDatabase.child("users").child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Map<String, Object> profile = (Map<String, Object>) task.getResult().getValue();
                        if (profile != null) {
                            editTextName.setText(profile.get("name").toString());
                            editTextEmail.setText(profile.get("email").toString());
                            // Split country code and phone number
                            String phone = profile.get("phone").toString();
                            String[] phoneParts = phone.split(" ", 2);
                            if (phoneParts.length == 2) {
                                spinnerCountryCode.setSelection(getIndex(spinnerCountryCode, phoneParts[0]));
                                editTextPhone.setText(phoneParts[1]);
                            }
                        }
                    }
                } else {
                    Toast.makeText(PersonalActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                }
            });
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
//spark
