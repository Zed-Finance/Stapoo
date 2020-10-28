package com.orion.stapoo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;

public class ResultActivity extends AppCompatActivity {
    PrefManager prefManager;
    LottieAnimationView animationView;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        prefManager = new PrefManager(this);
        animationView = findViewById(R.id.animationView);
        back = findViewById(R.id.btn_back);

        String score = getIntent().getStringExtra("score");

        if (Integer.parseInt(score) >= 3) {
            animationView.setAnimation("party.json");
        } else {
            animationView.setAnimation("improve.json");
        }
        animationView.playAnimation();
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        TextView tvScore = findViewById(R.id.tv_score);
        TextView tvUsername = findViewById(R.id.tv_username);
        tvScore.setText(String.format("You have scored %s out of 5", score));
        tvUsername.setText(prefManager.getUsername());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}