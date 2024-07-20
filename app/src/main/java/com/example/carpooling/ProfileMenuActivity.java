package com.example.carpooling;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class ProfileMenuActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    NavigationView navigationView;
    private TextView edit_text;
    private TextView verify_id;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_menu);

        drawerLayout = findViewById(R.id.menu);
        imageView = findViewById(R.id.menuDrawer2);
        navigationView = findViewById(R.id.nav_view);
        preferenceManager = new PreferenceManager(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.about){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked About", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.edit){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Edit", Toast.LENGTH_SHORT).show();
                } else if (itemId == R.id.logout){
                    logout();
                } else if (itemId == R.id.view_statistics){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Statistics", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.close();

                return true;
            }
        });

        edit_text = findViewById(R.id.personaldetails);
        verify_id = findViewById(R.id.verifyid);

        edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent i4 = new Intent(getApplicationContext(), PersonalActivity.class);
                startActivity(i4);
            }
        });

        verify_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Intent i5 = new Intent(getApplicationContext(), VerifyidActivity.class);
                startActivity(i5);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });
    }

    private void logout() {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMenuActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");

        // Set positive button to handle the logout action
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Clear user data from SharedPreferences
                preferenceManager.clear();

                // Navigate to LoginActivity
                Intent intent = new Intent(ProfileMenuActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Set negative button to dismiss the dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
