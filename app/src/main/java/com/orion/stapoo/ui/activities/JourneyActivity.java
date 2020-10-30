package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        ImageView home = findViewById(R.id.home);
        ImageView speaker = findViewById(R.id.speaker);

        speaker.setOnClickListener(this);
        home.setOnClickListener(this);

        CardView levelOne = findViewById(R.id.level1);
        CardView levelTwo = findViewById(R.id.level2);
        CardView levelThree = findViewById(R.id.level3);
        CardView levelFour = findViewById(R.id.level4);
        CardView levelFive = findViewById(R.id.level5);

        levelOne.setOnClickListener(this);

        levelTwo.setOnClickListener(this);

        levelThree.setOnClickListener(this);

        levelFour.setOnClickListener(this);

        levelFive.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level1:
                day = 0;
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", day.toString());
                startActivity(intent);
                break;
            case R.id.level2:
                day = 1;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.level3:
                day = 2;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.level4:
                day = 3;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.level5:
                day = 4;
                checkPrevProgressAndNavigate(day.toString(), String.valueOf(day - 1));
                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finishAffinity();
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