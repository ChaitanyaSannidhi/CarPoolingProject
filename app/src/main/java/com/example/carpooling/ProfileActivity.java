package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private TextView edit_text;
    private TextView verify_id;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    ImageView menuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linprofile), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edit_text = findViewById(R.id.personaldetails);
        verify_id = findViewById(R.id.verifyid);

        ImageView btn1 = findViewById(R.id.homebtn);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent i2 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i2);
            }
        });

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

        ImageView btn3 = findViewById(R.id.inboxbutton);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                Intent i3 = new Intent(getApplicationContext(), InboxActivity.class);
                startActivity(i3);
            }
        });
    }

}
//spark