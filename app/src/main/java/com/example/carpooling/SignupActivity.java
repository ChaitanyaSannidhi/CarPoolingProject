package com.example.carpooling;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;


public class SignupActivity extends AppCompatActivity {
    private PreferenceManager preferenceManager;

    private FirebaseAuth mAuth;
    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private TextView haveAcc;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseFirestore db;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        preferenceManager = new PreferenceManager(this);

        Button signupButton = findViewById(R.id.button2);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle signup logic here

                // After successful signup
                preferenceManager.setLoggedIn(true);
                startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                finish();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signuphead), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.enteremailid);
        passwordEditText = findViewById(R.id.signpass);
        registerButton = findViewById(R.id.button2);
        haveAcc = findViewById(R.id.haveacc);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    // User Already Logged in
                    // Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
                } else {
                    // No user yet!
                }
            }
        };

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(emailEditText.getText().toString()) &&
                        !TextUtils.isEmpty(passwordEditText.getText().toString())) {
                    String email = emailEditText.getText().toString().trim();
                    String password = passwordEditText.getText().toString().trim();
                    String username = usernameEditText.getText().toString().trim();
                    createUserEmailAccount(email, password, username);
                } else {
                    Toast.makeText(SignupActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set up have account text click listener
        haveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Toast.makeText(SignupActivity.this, "Working", Toast.LENGTH_SHORT).show();
                Intent i2 = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i2);
            }
        });
    }

    private void createUserEmailAccount(String email, String password, final String username) {
        registerButton.setEnabled(false);
        if (!TextUtils.isEmpty(emailEditText.getText().toString()) &&
                !TextUtils.isEmpty(passwordEditText.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Get current user after successful sign up
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if (currentUser != null) {
                                    final String currentUserId = currentUser.getUid();
                                    DocumentReference userDocRef = db.collection("Users").document(currentUserId);
                                    // Create a user map so we can create a user in the Collection
                                    Map<String, String> userObj = new HashMap<>();
                                    userObj.put("email", email);
                                    userObj.put("username", username);
                                    // Adding Users to Firestore
                                    userDocRef.set(userObj)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    userDocRef.get()
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        DocumentSnapshot document = task.getResult();
                                                                        if (document.exists()) {
                                                                            String name = document.getString("username");
                                                                            Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            // Document doesn't exist
                                                                        }
                                                                    } else {
                                                                        // Handle errors while retrieving the document
                                                                        Toast.makeText(SignupActivity.this, "Error getting document: " + task.getException(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                    // Navigate to login after successful registration
                                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Handle errors while setting the document data
                                                    Toast.makeText(SignupActivity.this, "Error setting document data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                registerButton.setEnabled(true);
                                Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            registerButton.setEnabled(true);
                            if (e instanceof FirebaseAuthUserCollisionException) {
                                emailEditText.setError("Email already exists");
                                emailEditText.requestFocus();
                            } else {
                                Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }
        

    }
}




