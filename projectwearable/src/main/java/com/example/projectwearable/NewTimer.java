package com.example.projectwearable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class NewTimer extends WearableActivity {
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

        private Button mainBtn;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_new_timer);
            chronometer=findViewById(R.id.timerChrono);
            startChrono=(findViewById(R.id.startChrono));
            displayDate = (TextView)findViewById(R.id.tvDate);
            distanceEt = (EditText)findViewById(R.id.distanceEt);
            saveTimer = (Button)findViewById(R.id.saveTimer);

            times= new Times();
            dbRef = FirebaseDatabase.getInstance().getReference().child("times");
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

            displayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(NewTimer.this,android.R.style.Widget_Holo_Light,tDateSetListener, year, month, day);
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

            saveTimer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int finishedDistance = Integer.parseInt(distanceEt.getText().toString().trim());

                    times.setDate(displayDate.getText().toString().trim());
                    times.setTime(chronometer.getText().toString().trim());
                    times.setDistance(finishedDistance);

                    dbRef.child(String.valueOf(maxId+1)).setValue(times);
                    Toast.makeText(NewTimer.this, "Data Success", Toast.LENGTH_LONG).show();
                }
            });
        mainBtn = (Button) findViewById(R.id.homeBtn);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewMain();
            }
        });
        }
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
        public void resetChronometer(View v){
            chronometer.setBase(SystemClock.elapsedRealtime());
            pauseOffset=0;
        }
    public void openNewMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
