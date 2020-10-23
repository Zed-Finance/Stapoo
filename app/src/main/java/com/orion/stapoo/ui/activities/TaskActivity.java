package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.orion.stapoo.R;

public class TaskActivity extends AppCompatActivity {

    String subject;
    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        subject = getIntent().getStringExtra("subject");
        day = getIntent().getStringExtra("day");


        CardView cardVideo = findViewById(R.id.card_video);
        CardView cardQuiz = findViewById(R.id.card_quiz);

        cardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
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

    }

}