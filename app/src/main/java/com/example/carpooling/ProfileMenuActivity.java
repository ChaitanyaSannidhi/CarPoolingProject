package com.example.carpooling;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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
        imageView = findViewById(R.id.menuDrawer);

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

                if (itemId == R.id.edit){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Edit", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.logout){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Logout", Toast.LENGTH_SHORT).show();
                }
                if (itemId == R.id.view_statistics){
                    Toast.makeText(ProfileMenuActivity.this, "Clicked Statistics", Toast.LENGTH_SHORT).show();
                }
                drawerLayout.close();

                return false;
            }
        });
    }
}
