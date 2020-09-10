package com.example.projectwearable;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Button newTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
        setAmbientEnabled();
        newTimer = (Button) findViewById(R.id.btnTimer);
        newTimer.setOnClickListener(new View.OnClickListener() {
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
