package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {
    private TextView edit_text;
    private TextView verify_id;
    private TextView verifyphonenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        edit_text = findViewById(R.id.personaldetails);
        verify_id = findViewById(R.id.verifyid);
        verifyphonenum = findViewById(R.id.verify_phonenum);

        // Set click listeners
        edit_text.setOnClickListener(v -> {
            Intent i4 = new Intent(getApplicationContext(), PersonalActivity.class);
            startActivity(i4);
        });

        verify_id.setOnClickListener(v -> {
            Intent i5 = new Intent(getApplicationContext(), VerifyidActivity.class);
            startActivity(i5);
        });

        verifyphonenum.setOnClickListener(v -> {
            Intent i6 = new Intent(getApplicationContext(),Phoneverification2Activity.class);
            startActivity(i6);
        });

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_profile) {
                    Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(ProfileActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                    return true;
                } else if (id == R.id.navigation_home) {
                    Toast.makeText(ProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    return true;
                } else if (id == R.id.navigation_inbox) {
                    Toast.makeText(ProfileActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
}