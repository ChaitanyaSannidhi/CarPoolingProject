package com.example.carpooling;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        Button btnOffer = findViewById(R.id.Offerbutton);
        btnOffer.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Offer Button Clicked", Toast.LENGTH_SHORT).show();
            Intent offerIntent = new Intent(getApplicationContext(), OfferActivity.class);
            startActivity(offerIntent);
        });

        Button btnRequest = findViewById(R.id.Requestbutton);
        btnRequest.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Request Button Clicked", Toast.LENGTH_SHORT).show();
            Intent requestIntent = new Intent(getApplicationContext(), RequestActivity.class);
            startActivity(requestIntent);
        });

        ImageView btnProfile = findViewById(R.id.profilebutton);
        btnProfile.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Profile Button Clicked", Toast.LENGTH_SHORT).show();
            Intent profileIntent = new Intent(getApplicationContext(), ProfileMenuActivity.class);
            startActivity(profileIntent);
        });

        ImageView btnInbox = findViewById(R.id.inboxbutton);
        btnInbox.setOnClickListener(v -> {
            Toast.makeText(HomeActivity.this, "Inbox Button Clicked", Toast.LENGTH_SHORT).show();
            Intent inboxIntent = new Intent(getApplicationContext(), InboxActivity.class);
            startActivity(inboxIntent);
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
