package com.example.project3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewRoutine extends AppCompatActivity {
    private EditText txtName, txtType, txtExercises, ex1RepTxt, ex1SetTxt, ex2RepTxt, ex2SetTxt;
    private Button btnSave;
    private DatabaseReference dbRef;
    private Workouts workouts;
    private long maxId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);
        setupUI(findViewById(R.id.routine_layout));

        txtName = (EditText)findViewById(R.id.txtName);
        txtType = (EditText)findViewById(R.id.txtType);
        ex1RepTxt = (EditText)findViewById(R.id.ex1RepTxt);
        ex1SetTxt = (EditText)findViewById(R.id.ex1SetTxt);
        ex2RepTxt = (EditText)findViewById(R.id.ex2RepTxt);
        ex2SetTxt = (EditText)findViewById(R.id.ex2SetTxt);
        txtExercises = (EditText)findViewById(R.id.txtExercises);
        btnSave=(Button)findViewById(R.id.btnSave);
        workouts=new Workouts();
        dbRef = FirebaseDatabase.getInstance().getReference("workouts");
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


        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // Convert string to integer
                int exercises=Integer.parseInt(txtExercises.getText().toString().trim());
                int reps1=Integer.parseInt(ex1RepTxt.getText().toString().trim());
                int sets1=Integer.parseInt(ex1SetTxt.getText().toString().trim());
                int reps2=Integer.parseInt(ex2RepTxt.getText().toString().trim());
                int sets2=Integer.parseInt(ex2SetTxt.getText().toString().trim());

                // setting the database data using setters from Workouts
                workouts.setName(txtName.getText().toString().trim());
                workouts.setType(txtType.getText().toString().trim());
                workouts.setExercises(exercises);
                workouts.setEx1Reps(reps1);
                workouts.setEx1Sets(sets1);
                workouts.setEx2Reps(reps2);
                workouts.setEx2Sets(sets2);
                dbRef.child(String.valueOf(maxId+1)).setValue(workouts);

                Toast.makeText(NewRoutine.this, "Data Success", Toast.LENGTH_LONG).show();
        }
        });
        // Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);//maybe dashboard
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
                    hideSoftKeyboard(NewRoutine.this);
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
