package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorSignUp extends AppCompatActivity {

    TextInputLayout doctorName, qualificatioin, address, hpr_id, mobileNumber, email, doctor_username, password;
    EditText doctorNameEditText, qualificatioinEditText, addressEditText, hpr_idEditText, mobileNumberEditText, emailEditText, usernameEditText;
    String gender;
    RadioGroup radioGenderGroup;
    Button nextPage;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        doctorName = findViewById(R.id.doctor_name);
        address = findViewById(R.id.address);
        hpr_id = findViewById(R.id.hpr_id);
        nextPage = findViewById(R.id.next_page);
        doctor_username = findViewById(R.id.doctor_username);
        password = findViewById(R.id.password);

        doctorNameEditText = doctorName.getEditText();
        addressEditText = address.getEditText();
        hpr_idEditText = hpr_id.getEditText();
        usernameEditText = doctor_username.getEditText();

        nextPage.setOnClickListener((view) -> {

            String name, qualificationText, addressText, hpr_idText, mobileNumberText, username;
            name = doctorNameEditText.getText().toString();
            username = usernameEditText.getText().toString();
            hpr_idText = hpr_idEditText.getText().toString();
            addressText = addressEditText.getText().toString();
            Intent intent = new Intent(this, doctorSignUp2.class);
            intent.putExtra("doctorName", name);
            intent.putExtra("username", username);
            intent.putExtra("hpr_id", hpr_idText);
            intent.putExtra("address", addressText);
            intent.putExtra("password", password.getEditText().getText().toString());
            startActivity(intent);
        });
    }



    private Boolean validateEmail() {
        String val = email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            email.setError("invalid email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
}