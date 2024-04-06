package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class doctorSignIn extends AppCompatActivity {

    Button signUPButton, login_button;
    ImageView image;
    TextView logoText, sloganText;
    TextInputLayout username, password;
    EditText usernameEditText, passwordEditText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_in);

        // Initialize views
        signUPButton = findViewById(R.id.signUp);
        login_button = findViewById(R.id.login_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        usernameEditText = username.getEditText();
        passwordEditText = password.getEditText();

        login_button.setOnClickListener(view -> {
            loginUser(view);
        });

        signUPButton.setOnClickListener((view) -> {
            Intent intent = new Intent(this, DoctorSignUp.class);
            startActivity(intent);
        });
    }
    public void loginUser(View view) {
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        String noWhiteSpaces = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else if (val.length()>=15){
            username.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpaces)) {
            username.setError("White spaces are allowed");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();

        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private void isUser() {
        String userEnteredUsername = usernameEditText.getText().toString();
        String userEnteredPassword = passwordEditText.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("doctor");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    // Iterate through each doctor node
                    for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = doctorSnapshot.child("password").getValue(String.class);

                        if(passwordFromDB != null && passwordFromDB.equals(userEnteredPassword)) {
                            username.setError(null);
                            username.setErrorEnabled(false);

                            //   Intent intent = new Intent(doctorSignIn.this, Home.class);
                            //   startActivity(intent);
                            return; // Exit the loop after finding the correct password
                        }
                    }
                    // If the loop finishes without finding a matching password
                    password.setError("Wrong Password");
                } else {
                    username.setError("No such user exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

}