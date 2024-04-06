package com.example.myapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search extends Fragment {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;

    private DatabaseReference databaseReference;

    public Search() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.recyclerViewDoctors);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize Doctor list
        doctorList = new ArrayList<>();

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("doctors");

        // Add ValueEventListener to fetch doctors' data
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear(); // Clear existing data
                List<Doctor> allDoctors = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Parse Doctor object from dataSnapshot
                    Doctor doctor = snapshot.getValue(Doctor.class);
                    allDoctors.add(doctor);
                }
                // Shuffle the list to get random doctors
                Collections.shuffle(allDoctors);

                // Add first 10 doctors to the list
                for (int i = 0; i < Math.min(10, allDoctors.size()); i++) {
                    doctorList.add(allDoctors.get(i));
                }

                // Notify adapter about data change
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        // Initialize and set adapter
        doctorAdapter = new DoctorAdapter(doctorList);
        recyclerView.setAdapter(doctorAdapter);

        return rootView;
    }
}