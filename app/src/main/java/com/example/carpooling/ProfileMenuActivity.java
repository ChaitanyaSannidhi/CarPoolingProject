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
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ProfileMenuActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView imageView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_menu);

        drawerLayout = findViewById(R.id.menu);
        imageView = findViewById(R.id.menuDrawer2);
        navigationView = findViewById(R.id.nav_view);


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
                }

                else if (itemId == R.id.edit){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Edit", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.logout){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Logout", Toast.LENGTH_SHORT).show();
                }
                else if (itemId == R.id.view_statistics){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Statistics", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.close();

                return true;
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_inbox) {
                    Toast.makeText(ProfileMenuActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    return true;
                } else if (id == R.id.navigation_rides) {
                    Toast.makeText(ProfileMenuActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                    return true;
                } else if (id == R.id.navigation_home) {
                    Toast.makeText(ProfileMenuActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
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
}
