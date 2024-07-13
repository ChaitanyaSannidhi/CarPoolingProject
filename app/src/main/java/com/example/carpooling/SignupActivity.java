package com.example.carpooling;
import static android.content.ContentValues.TAG;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Replace with your web client ID
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        ImageView signinwithgoogle = findViewById(R.id.signgoogle);
        signinwithgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInWithGoogle();
            }
        });

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
    private void SignInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign-In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign-In failed, handle this scenario
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(SignupActivity.this, "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Check if user is new or existing and handle accordingly
                                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                                if (isNewUser) {
                                    // Handle new user registration in Firestore
                                    String username = user.getDisplayName(); // Example: Use username from Google
                                    String email = user.getEmail(); // Example: Use email from Google
                                    createUserGoogleAccount(email, username);
                                } else {
                                    // Existing user, navigate to home activity
                                    navigateToHomeActivity();
                                }
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void createUserGoogleAccount(String email, String username) {
        registerButton.setEnabled(false); // Disable button during account creation

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username)) {
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                final String currentUserId = currentUser.getUid();
                DocumentReference userDocRef = db.collection("GoogleUsers").document(currentUserId);

                // Create a user map
                Map<String, Object> userObj = new HashMap<>();
                userObj.put("email", email);
                userObj.put("username", username);
                // Add more fields as needed

                // Add the user to Firestore
                userDocRef.set(userObj)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                // Document added successfully
                                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                navigateToHomeActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error adding document
                                Toast.makeText(SignupActivity.this, "Error adding document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                registerButton.setEnabled(true); // Enable button again for retry
                            }
                        });
            } else {
                Toast.makeText(SignupActivity.this, "Current user is null", Toast.LENGTH_SHORT).show();
                registerButton.setEnabled(true); // Enable button again for retry
            }
        } else {
            Toast.makeText(SignupActivity.this, "Email or Username is empty", Toast.LENGTH_SHORT).show();
            registerButton.setEnabled(true); // Enable button again for retry
        }
    }

    private void navigateToHomeActivity() {
        // Navigate to HomeActivity after successful sign-in
        preferenceManager.setLoggedIn(true);
        startActivity(new Intent(SignupActivity.this, HomeActivity.class));
        finish();
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




