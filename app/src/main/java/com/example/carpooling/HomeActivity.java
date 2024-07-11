package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btn2 = findViewById(R.id.Offerbutton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getApplicationContext(), OfferActivity.class);
                startActivity(i2);
            }
        });
        Button btn1 = findViewById(R.id.Requestbutton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getApplicationContext(), RequestActivity.class);
                startActivity(i2);
            }
        });
        ImageView btn3 = findViewById(R.id.profilebutton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Toast.makeText(HomeActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(i3);
            }
        });
    }
}