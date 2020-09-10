package com.example.project3;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase db;

    private DatabaseReference workoutsRef;
    private List<Workouts> workouts = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Workouts> workouts, List<String> keys);
        void DataIsInsert();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        workoutsRef = db.getReference().child("workouts");
    }
    /// Helper for workouts database
    public void readWorkouts(final DataStatus dataStatus){
        workoutsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workouts.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Workouts workout = keyNode.getValue(Workouts.class);
                    workouts.add(workout);
                }
                dataStatus.DataIsLoaded(workouts, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
