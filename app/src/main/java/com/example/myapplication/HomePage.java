package com.example.myapplication;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomePage extends Fragment {

    private CardView videoConsultation, onlineMedicine, bookTest, sosCard;
    private static final int REQUEST_CALL_PERMISSION = 1;
    public HomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        videoConsultation = view.findViewById(R.id.video_consultation);
        onlineMedicine = view.findViewById(R.id.online_medicine);
        sosCard = view.findViewById(R.id.sos_card);
        bookTest = view.findViewById(R.id.book_test);

        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            // Permission is already granted, proceed with making a call

        }

        // Set OnClickListener for the videoConsultation CardView
        videoConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the VideoConsultation activity
                startActivity(new Intent(getActivity(), Chat_activity.class));
            }
        });

        // Set OnClickListener for the onlineMedicine CardView
        onlineMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the OnlineMedicineActivity activity
                startActivity(new Intent(getActivity(), OnlineMedicineActivity.class));
            }
        });

        bookTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PenTestActivity.class));
            }
        });

        sosCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+919890109975"));
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with making a call

            } else {
                // Permission denied
                Toast.makeText(getContext(), "Permission denied. You cannot make a call.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}