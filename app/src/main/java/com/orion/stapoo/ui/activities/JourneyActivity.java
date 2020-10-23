package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.orion.stapoo.R;

public class JourneyActivity extends AppCompatActivity {

    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        subject = getIntent().getStringExtra("subject");

        Button levelOne = findViewById(R.id.level_1);
        Button levelTwo = findViewById(R.id.level_2);

        levelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", "0");
                startActivity(intent);
            }
        });

        levelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", "1");
                startActivity(intent);
            }
        });

    }
}