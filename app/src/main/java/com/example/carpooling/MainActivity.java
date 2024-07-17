package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if(preferenceManager.isLoggedIn()){
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btn1 = findViewById(R.id.logButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
            }
        });
        Button btn2 = findViewById(R.id.signupButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Toast.makeText(MainActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(i2);
            }
        });
        //adding contributor

    }
}
//spark