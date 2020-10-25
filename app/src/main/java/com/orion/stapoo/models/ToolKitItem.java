package com.orion.stapoo.models;

public class ToolKitItem {
    String title;
    String videoId;

    public ToolKitItem() {
    }

    public ToolKitItem(String title, String videoId) {
        this.title = title;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
