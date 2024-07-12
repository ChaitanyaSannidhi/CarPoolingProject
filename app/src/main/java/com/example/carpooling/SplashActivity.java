package com.example.carpooling;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private PreferenceManager preferenceManager;    private static final int SPLASH_DISPLAY_LENGTH = 2000; // Duration of wait (in milliseconds)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView splashLogo = findViewById(R.id.splashlogo);
        preferenceManager = new PreferenceManager(this);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferenceManager.isLoggedIn()) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 2000); // 3 seconds delay
        // Load and start the animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        splashLogo.startAnimation(fadeIn);

        // New Handler to start the LoginActivity and close this SplashScreen after some seconds.
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the LoginActivity.
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish(); // Close the SplashActivity so the user won't be able to go back to it.
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
