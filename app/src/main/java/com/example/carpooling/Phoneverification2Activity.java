package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Phoneverification2Activity extends AppCompatActivity {
    private Button sendButton;
    private TextView phnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_phoneverification2);
        phnum = findViewById(R.id.enterphnum);
        Button btn2 = findViewById(R.id.btn_send);
        btn2.setOnClickListener(v2 -> {
            Toast.makeText(Phoneverification2Activity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), PhoneVerificationActivity.class);
            startActivity(i2);
        });

    }
}