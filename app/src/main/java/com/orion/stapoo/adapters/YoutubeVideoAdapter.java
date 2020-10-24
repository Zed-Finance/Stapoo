package com.orion.stapoo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.stapoo.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;


public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoViewHolder> {
    private List<String> youtubeLinkList;

    public YoutubeVideoAdapter(List<String> youtubeLinkList) {
        this.youtubeLinkList = youtubeLinkList;
    }

    @NonNull
    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YoutubeVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull YoutubeVideoViewHolder holder, int position) {
        final String videoId = youtubeLinkList.get(position);
        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeLinkList.size();
    }

    public static class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;

        public YoutubeVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
        }
    }
}
