package com.example.carpooling;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class VerifyidActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyid);

        ImageView btn1 = findViewById(R.id.backarrow);
        btn1.setOnClickListener(v -> {
            Toast.makeText(VerifyidActivity.this, "Working", Toast.LENGTH_SHORT).show();
            Intent i2 = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(i2);
        });

        Button enableCameraButton = findViewById(R.id.cambutton);

        // Prepare camera launcher
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Handle the result if needed
                        Toast.makeText(this, "Camera opened successfully", Toast.LENGTH_SHORT).show();
                    }
                });

        // Prepare permission request launcher
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                // Permission is granted. Open the camera.
                openCamera();
            } else {
                // Permission denied. Show a message to the user.
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for enableCameraButton
        enableCameraButton.setOnClickListener(v -> {
            // Request camera permission every time the button is clicked
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            } else {
                // Permission already granted, open the camera
                openCamera();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
}
