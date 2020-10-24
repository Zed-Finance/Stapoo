package com.orion.stapoo.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orion.stapoo.R;
import com.orion.stapoo.adapters.YoutubeVideoAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class ToolkitActivity extends AppCompatActivity {
    List<String> youtubeLinkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolkit);

      /*  final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //final ProgressBar progressBar = findViewById(R.id.progress_bar);
        youtubeLinkList = new ArrayList<>();
*/
        YouTubePlayerView youTubePlayerView1 = findViewById(R.id.youtube_player_view1);
        YouTubePlayerView youTubePlayerView2 = findViewById(R.id.youtube_player_view2);
        YouTubePlayerView youTubePlayerView3 = findViewById(R.id.youtube_player_view3);

        getLifecycle().addObserver(youTubePlayerView1);
        getLifecycle().addObserver(youTubePlayerView2);
        getLifecycle().addObserver(youTubePlayerView3);

        youTubePlayerView1.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("dH0yz-Osy54", 0);
            }
        });


        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("GG5xBwbje1E", 0);
            }
        });

        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("2ePf9rue1Ao", 0);
            }
        });
    }
}



      /*  FirebaseDatabase.getInstance().getReference().child("toolKitLinks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                youtubeLinkList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    youtubeLinkList.add(dataSnapshot.getValue(String.class));
                }
                recyclerView.setAdapter(new YoutubeVideoAdapter(youtubeLinkList));
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                //progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
