package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    Button gotoQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        fetchAndPlayVideo(getIntent().getStringExtra("subject"), getIntent().getStringExtra("day"));

        gotoQuiz = findViewById(R.id.btn_quiz);
        gotoQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("subject", getIntent().getStringExtra("subject"));
                intent.putExtra("day", getIntent().getStringExtra("day"));
                startActivity(intent);
                finish();
            }
        });
    }

    private void fetchAndPlayVideo(String subject, String day) {
        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("video").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String videoId = snapshot.getValue(String.class);
                playVideo(videoId);
                startTimer();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void playVideo(final String videoId) {
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    private void startTimer() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int color = Color.parseColor("#FF76C8");
                gotoQuiz.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                gotoQuiz.setEnabled(true);
            }
        }, 5000);
    }

}