package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.orion.stapoo.R;

public class LearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        ImageView imgMaths = findViewById(R.id.img_maths);
        ImageView imgEnglish = findViewById(R.id.img_english);

        ImageView home = findViewById(R.id.home);
        ImageView speaker = findViewById(R.id.speaker);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        imgMaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JourneyActivity.class);
                intent.putExtra("subject", "maths");
                startActivity(intent);
            }
        });

        imgEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), JourneyActivity.class);
                intent.putExtra("subject", "english");
                startActivity(intent);
            }
        });
    }
}