package com.example.carpooling;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import com.example.carpooling.R;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(HomeActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                    return true;
                } else if (id == R.id.navigation_inbox) {
                    Toast.makeText(HomeActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Toast.makeText(HomeActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ProfileMenuActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Existing button setup
        Button btn2 = findViewById(R.id.Offerbutton);
        btn2.setOnClickListener(v2 -> {
            Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), OfferActivity.class);
            startActivity(i2);
        });

        Button btn1 = findViewById(R.id.Requestbutton);
        btn1.setOnClickListener(v2 -> {
            Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), RequestActivity.class);
            startActivity(i2);
        });



    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Create an alert dialog
        new AlertDialog.Builder(this)
                .setTitle("Exit Application")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Exit the application
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
