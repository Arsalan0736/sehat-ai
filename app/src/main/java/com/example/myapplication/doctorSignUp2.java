package com.example.myapplication;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class doctorSignUp2 extends AppCompatActivity {

    TextInputLayout email, phoneNumber, qualification;
    RadioGroup radioGenderGroup;
    String gender, getName, getUsername, getHPR_id, getAddress, getPassword;
    Button registerButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_sign_up2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radioGenderGroup = findViewById(R.id.radioGroupGender);
        registerButton = findViewById(R.id.registerButton);
        email = findViewById(R.id.email);
        qualification = findViewById(R.id.qualification);
        phoneNumber = findViewById(R.id.mobile_number);

        Intent intent = getIntent();

        getName = intent.getStringExtra("doctorName");
        getUsername = intent.getStringExtra("username");
        getHPR_id = intent.getStringExtra("hpr_id");
        getAddress = intent.getStringExtra("address");
        getPassword = intent.getStringExtra("password");


        radioGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected based on its ID
                if (checkedId == R.id.radioButtonMale) {
                    gender = "Male";
                } else if (checkedId == R.id.radioButtonFemale) {
                    // Handle female selection
                    gender = "Female";

                }
            }
        });

        registerButton.setOnClickListener((view) -> {
            Doctor doctor = new Doctor(getName, qualification.getEditText().getText().toString(), getAddress, getHPR_id, gender, phoneNumber.getEditText().getText().toString(), getUsername, getPassword, email.getEditText().getText().toString());
            addDoctorToDatabase(doctor);
        });
    }
    private void addDoctorToDatabase(Doctor doctor) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("doctor");
        String doctorKey = databaseReference.push().getKey();
        databaseReference.child(doctorKey).setValue(doctor);

        Toast.makeText(this, "Your account has been created successfully", Toast.LENGTH_SHORT).show();
//
    }
}