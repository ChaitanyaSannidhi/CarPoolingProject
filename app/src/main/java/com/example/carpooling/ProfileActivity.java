package com.example.carpooling;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private TextView edit_text;
    private TextView verify_id;
    private ImageView profileImage;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int REQUEST_PERMISSIONS = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.navigation_profile);

        checkPermissions();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linprofile), (v, insets) -> {
            //WindowInsetsCompat insetsCompat = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(insetsCompat.left, insetsCompat.top, insetsCompat.right, insetsCompat.bottom);
            return insets;
        });

        edit_text = findViewById(R.id.personaldetails);
        verify_id = findViewById(R.id.verifyid);

        edit_text.setOnClickListener(v1 -> {
            Intent i4 = new Intent(getApplicationContext(), PersonalActivity.class);
            startActivity(i4);
        });

        verify_id.setOnClickListener(v3 -> {
            Intent i5 = new Intent(getApplicationContext(), VerifyidActivity.class);
            startActivity(i5);
        });

        profileImage.setOnClickListener(v -> showImagePickerDialog());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.navigation_profile) {
                Toast.makeText(ProfileActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.navigation_rides) {
                Toast.makeText(ProfileActivity.this, "Your Rides", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), YourRidesActivity.class));
                return true;
            } else if (id == R.id.navigation_home) {
                Toast.makeText(ProfileActivity.this, "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                return true;
            } else if (id == R.id.navigation_inbox) {
                Toast.makeText(ProfileActivity.this, "Inbox", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                return true;
            }
            return false;
        });
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        String[] options = {"Take Photo", "Choose from Gallery"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            } else if (which == 1) {
                openGallery();
            }
        });
        builder.create().show();
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                profileImage.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_GALLERY && data != null) {
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
