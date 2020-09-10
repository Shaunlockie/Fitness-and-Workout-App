package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewTimer extends AppCompatActivity {

    private static final String TAG = "NewTimer";
    private DatabaseReference dbRef;
    private EditText distanceEt;
    private Chronometer chronometer;
    private Button saveTimer;
    private Button startChrono;
    private long pauseOffset;
    private boolean running;
    private TextView displayDate;
    private Times times;
    private DatePickerDialog.OnDateSetListener tDateSetListener;
    private long maxId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_timer);
        setupUI(findViewById(R.id.timer_layout));

        chronometer=findViewById(R.id.timerChrono);
        startChrono=(findViewById(R.id.startChrono));
        displayDate = (TextView)findViewById(R.id.tvDate);
        distanceEt = (EditText)findViewById(R.id.distanceEt);
        saveTimer = (Button)findViewById(R.id.saveTimer);
        times= new Times();

        dbRef = FirebaseDatabase.getInstance().getReference("times");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxId=(dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Allow user to set date with dialog option
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NewTimer.this,tDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();
            }
        });
        tDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month= month+1;
                Log.d(TAG, "date:" + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
                displayDate.setText(date);
            }
        };
        // setting the database data using setters from Workouts
        saveTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int finishedDistance = Integer.parseInt(distanceEt.getText().toString().trim());
                String finishedDate = displayDate.getText().toString().trim();
                String finishedTime = chronometer.getText().toString().trim();
                // setting the database data using setters from Times
                times.setDate(finishedDate);
                times.setTime(finishedTime);
                times.setDistance(finishedDistance);

                dbRef.child(String.valueOf(maxId+1)).setValue(times);
                Toast.makeText(NewTimer.this, "Data Success", Toast.LENGTH_LONG).show();
            }
        });
        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.timer);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.routine:
                        startActivity(new Intent(getApplicationContext(), Routine.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.timer:
                        startActivity(new Intent(getApplicationContext(), Timer.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    // starting and pausing chronometer
    public void startChronometer(View v){
        if(!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            startChrono.setText("Pause");
        }
        else{
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            startChrono.setText("Start");
        }
    }
    // reset the chronometer
    public void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset=0;
    }
    // Hiding keyboard when clicking off edit text
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewTimer.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}

