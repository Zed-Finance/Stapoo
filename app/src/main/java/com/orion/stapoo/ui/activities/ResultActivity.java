package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

public class ResultActivity extends AppCompatActivity {
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        prefManager = new PrefManager(this);

        String score = getIntent().getStringExtra("score");

        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvUsername = findViewById(R.id.tv_username);
        tvScore.setText(String.format("You have scored %s out of 5", score));
        tvUsername.setText(prefManager.getUsername());
    }
}