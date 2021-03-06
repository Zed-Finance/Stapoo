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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.utils.PrefManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class VideoActivity extends AppCompatActivity {

    YouTubePlayerView youTubePlayerView;
    Button gotoQuiz;
    String subject;
    String day;
    List<String> watchList;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        watchList = new ArrayList<>();

        subject = getIntent().getStringExtra("subject");
        day = getIntent().getStringExtra("day");
        username = new PrefManager(this).getUsername();


        fetchAndPlayVideo(subject, day);

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

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                findViewById(R.id.imageView3).setVisibility(View.VISIBLE);
                findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                gotoQuiz.setVisibility(View.GONE);
                findViewById(R.id.imageView4).setVisibility(View.GONE);
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                findViewById(R.id.imageView3).setVisibility(View.VISIBLE);
                findViewById(R.id.textView3).setVisibility(View.VISIBLE);
                gotoQuiz.setVisibility(View.VISIBLE);
                findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
            }
        });
    }

    private void startTimer() {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("watchList").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        watchList.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                watchList.add(snapshot1.getValue(String.class));
                            }
                        }
                        watchList.add(username);
                        FirebaseDatabase.getInstance().getReference().child("subjects").child(subject).child(day).child("watchList").setValue(watchList).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                int color = Color.parseColor("#B02243");
                                gotoQuiz.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
                                gotoQuiz.setEnabled(true);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }, 60000);
    }

}