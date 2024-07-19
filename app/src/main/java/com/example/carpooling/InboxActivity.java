package com.example.carpooling;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class InboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inbox);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_inbox);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_inbox) {
                    Toast.makeText(InboxActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(InboxActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                    return true;
                } else if (id == R.id.navigation_home) {
                    Toast.makeText(InboxActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    return true;
                } else if (id == R.id.navigation_profile) {
                    Toast.makeText(InboxActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ProfileMenuActivity.class));
                    return true;
                }
                return false;
            }
        });

    }
}
//spark