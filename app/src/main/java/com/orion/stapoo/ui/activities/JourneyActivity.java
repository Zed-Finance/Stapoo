package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class JourneyActivity extends AppCompatActivity implements View.OnClickListener {

    String subject;
    Integer day;
    String username;
    List<String> proofListKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        subject = getIntent().getStringExtra("subject");
        username = new PrefManager(this).getUsername();
        proofListKeys = new ArrayList<>();

        Button levelOne = findViewById(R.id.level_1);
        Button levelTwo = findViewById(R.id.level_2);
        Button levelThree = findViewById(R.id.level_3);
        Button levelFour = findViewById(R.id.level_4);

        levelOne.setOnClickListener(this);

        levelTwo.setOnClickListener(this);

        levelThree.setOnClickListener(this);

        levelFour.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level_1:
                day = 0;
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", day.toString());
                startActivity(intent);
                break;
            case R.id.level_2:
                day = 1;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.level_3:
                day = 2;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.level_4:
                day = 3;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;

        }
    }

    private void checkPrevProgressAndNavigate(final String currentDay, final String prevDay) {
        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(prevDay).child("proofList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                proofListKeys.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        proofListKeys.add(dataSnapshot.getKey());
                    }
                    if (proofListKeys.contains(username)) {
                        Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                        intent.putExtra("subject", subject);
                        intent.putExtra("day", currentDay);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please complete previous day's tasks", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please complete previous day's tasks", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}