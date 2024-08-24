package com.example.carpooling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView imageView;
    private NavigationView navigationView;
    private TextView edit_text;
    private TextView verify_id;
    private FirebaseFirestore db;
    private String userId;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_menu);

        drawerLayout = findViewById(R.id.menu);
        imageView = findViewById(R.id.menuDrawer2);
        navigationView = findViewById(R.id.nav_view);
        edit_text = findViewById(R.id.personaldetails);
        verify_id = findViewById(R.id.verifyid);
        preferenceManager = new PreferenceManager(this);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Set up navigation drawer
        imageView.setOnClickListener(v -> drawerLayout.open());

        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.about) {
                Intent i1 = new Intent(getApplicationContext(), UserDataActivity.class);
                startActivity(i1);
            } else if (itemId == R.id.edit) {
                Toast.makeText(ProfileMenuActivity.this, "Clicked Edit", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.logout) {
                logout();
            } else if (itemId == R.id.view_statistics) {
                Toast.makeText(ProfileMenuActivity.this, "Clicked Statistics", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.PhoneVerify) {
                Intent i2 = new Intent(getApplicationContext(), PhoneVerificationActivity.class);
                startActivity(i2);
            }
            drawerLayout.close();
            return true;
        });

        edit_text.setOnClickListener(v -> {
            Intent i4 = new Intent(getApplicationContext(), PersonalActivity.class);
            startActivity(i4);
        });

        verify_id.setOnClickListener(v -> {
            Intent i5 = new Intent(getApplicationContext(), VerifyidActivity.class);
            startActivity(i5);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_inbox) {
                Toast.makeText(ProfileMenuActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                return true;
            } else if (id == R.id.navigation_rides) {
                Toast.makeText(ProfileMenuActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                return true;
            } else if (id == R.id.navigation_home) {
                Toast.makeText(ProfileMenuActivity.this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                return true;
            } else if (id == R.id.navigation_profile) {
                Toast.makeText(ProfileMenuActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileMenuActivity.class));
                return true;
            }
            return false;
        });

        // Load user data into the header layout
        loadUserData();
    }

    private void loadUserData() {
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String username = document.getString("name");
                            String email = document.getString("email");

                            // Update header layout in the navigation drawer
                            View headerView = navigationView.getHeaderView(0);
                            TextView usernameTextView = headerView.findViewById(R.id.username);
                            TextView emailTextView = headerView.findViewById(R.id.email);

                            usernameTextView.setText(username);
                            emailTextView.setText(email);
                        } else {
                            Log.d("ProfileMenuActivity", "No such document");
                        }
                    } else {
                        Log.d("ProfileMenuActivity", "Get failed with ", task.getException());
                    }
                });
    }

    private void logout() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMenuActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");

        // Set positive button to handle the logout action
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Clear user data from SharedPreferences
            preferenceManager.clear();

            // Navigate to LoginActivity
            Intent intent = new Intent(ProfileMenuActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Set negative button to dismiss the dialog
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
