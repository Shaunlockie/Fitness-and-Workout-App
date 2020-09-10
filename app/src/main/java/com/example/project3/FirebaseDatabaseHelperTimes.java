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

public class FirebaseDatabaseHelperTimes {
    private FirebaseDatabase dbTime;
    private DatabaseReference timesRef;
    private List<Times> times = new ArrayList<>();

    public interface DataStatus{
        void DataIsLoaded(List<Times> times, List<String> keys);
        void DataIsInsert();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelperTimes() {
        dbTime = FirebaseDatabase.getInstance();
        timesRef = dbTime.getReference().child("times");
    }
    /// Helper for times database
    public void readTimes(final DataStatus dataStatus){
        timesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                times.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Times time = keyNode.getValue(Times.class);
                    times.add(time);
                }
                dataStatus.DataIsLoaded(times, keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

