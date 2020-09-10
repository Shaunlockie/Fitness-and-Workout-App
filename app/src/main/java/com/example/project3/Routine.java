package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class Routine extends AppCompatActivity {
    private Button newRoutine;
    private RecyclerView wRecyclerView;
    private DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        wRecyclerView = findViewById(R.id.routineRecycle);
        new FirebaseDatabaseHelper().readWorkouts(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Workouts> workouts, List<String> keys) {
                new RecyclerView_Config().setConfig(wRecyclerView, Routine.this, workouts,keys);
            }

            @Override
            public void DataIsInsert() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.routine);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.routine:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.timer:
                        startActivity(new Intent(getApplicationContext(), Timer.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        newRoutine = (Button) findViewById(R.id.NewRoutineBtn);
        newRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewRoutine();
            }
        });

    }

    public void openNewRoutine() {
        Intent intent = new Intent(this, NewRoutine.class);
        startActivity(intent);
    }

}

