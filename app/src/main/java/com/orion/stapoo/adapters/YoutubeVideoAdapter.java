package com.orion.stapoo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.orion.stapoo.R;
import com.orion.stapoo.interfaces.ClickInterface;
import com.orion.stapoo.models.ToolKitItem;
import java.util.List;


public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoViewHolder> {
    private List<ToolKitItem> youtubeLinkList;
    private ClickInterface clickInterface;

    public YoutubeVideoAdapter(List<ToolKitItem> youtubeLinkList, ClickInterface clickInterface) {
        this.youtubeLinkList = youtubeLinkList;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new YoutubeVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull YoutubeVideoViewHolder holder, int position) {
        final ToolKitItem toolKitItem = youtubeLinkList.get(position);

        holder.title.setText(toolKitItem.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.cardClickInterface(toolKitItem.getVideoId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeLinkList.size();
    }

    public static class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public YoutubeVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
