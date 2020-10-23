package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.orion.stapoo.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String score = getIntent().getStringExtra("score");

        TextView tvScore = findViewById(R.id.tv_score);
        tvScore.setText(String.format("You have scored %s out of 5", score));

    }
}