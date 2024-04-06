package com.example.myapplication;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OnlineMedicineActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_medicine);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMedicines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate medicine list
        medicineList = new ArrayList<>();
        populateMedicineList();

        // Set up adapter
        adapter = new MedicineAdapter(medicineList);
        recyclerView.setAdapter(adapter);
    }

    private void populateMedicineList() {
        // Add 10 sample medicines
        for (int i = 1; i <= 10; i++) {
            medicineList.add(new Medicine("Medicine " + i, "Description for Medicine " + i, 10.0 * i));
        }
    }
}