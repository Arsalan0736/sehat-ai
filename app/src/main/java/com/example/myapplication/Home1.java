package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home1 extends AppCompatActivity {

    Button patient_access, doctor_access;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        patient_access = findViewById(R.id.patient_access);
        doctor_access = findViewById(R.id.doctor_access);

        patient_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home1.this,MainActivity.class);
                startActivity(intent);
            }
        });

        doctor_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home1.this, doctorSignIn.class);
                startActivity(intent);
            }
        });


    }


}