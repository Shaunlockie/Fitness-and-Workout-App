package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Timer extends AppCompatActivity {
    private Button NewTimer;
    private RecyclerView tRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tRecyclerView = findViewById(R.id.timerRecycle);
        new FirebaseDatabaseHelperTimes().readTimes(new FirebaseDatabaseHelperTimes.DataStatus() {
            @Override
            public void DataIsLoaded(List<Times> times, List<String> keys) {
                new RecyclerView_ConfigTimes().setConfig(tRecyclerView, Timer.this, times,keys);
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

        bottomNavigationView.setSelectedItemId(R.id.timer);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.routine:
                        startActivity(new Intent(getApplicationContext(), Routine.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.timer:
                        return true;
                }
                return false;
            }
        });
    NewTimer = (Button) findViewById(R.id.NewTimerBtn);
    NewTimer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openNewTimer();
        }
    });
}
    public void openNewTimer() {
        Intent intent = new Intent(this, NewTimer.class);
        startActivity(intent);
    }
}
