package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    String subject;
    String day;
    List<String> watchlist;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        subject = getIntent().getStringExtra("subject");
        day = getIntent().getStringExtra("day");
        watchlist = new ArrayList<>();
        username = new PrefManager(this).getUsername();

        ImageView home = findViewById(R.id.home);
        ImageView speaker = findViewById(R.id.speaker);

        CardView cardVideo = findViewById(R.id.card_video);
        CardView cardQuiz = findViewById(R.id.card_quiz);
        CardView cardMaterial = findViewById(R.id.card_material);
        final CardView cardProof = findViewById(R.id.card_proof);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        });

        cardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("watchList").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        watchlist.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                watchlist.add(dataSnapshot.getValue(String.class));
                            }
                            if (watchlist.contains(username)) {
                                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                                intent.putExtra("subject", subject);
                                intent.putExtra("day", day);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Please watch the video first to unlock the quiz", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please watch the video first to unlock the quiz", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        cardMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CourseMaterialActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });

        cardVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });

        cardProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UploadProofActivity.class);
                intent.putExtra("subject", subject);
                intent.putExtra("day", day);
                startActivity(intent);
            }
        });
    }


}