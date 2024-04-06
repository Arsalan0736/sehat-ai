package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Bot;
import com.example.myapplication.HomePage;
import com.example.myapplication.R;
import com.example.myapplication.Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        FragmentTransaction Transaction = getSupportFragmentManager().beginTransaction();
        Transaction.replace(R.id.Home, new HomePage());

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment = null;

            if (menuItem.getItemId() == R.id.home_page) {
                fragment = new HomePage();
            } else if (menuItem.getItemId() == R.id.search) {
                fragment = new Search();
            } else {
                fragment = new Bot();
            }
            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Home, fragment);
            fragmentTransaction.commit();

            return true;
        });
    }
}