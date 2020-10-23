package com.orion.stapoo.models;

public class OptionsModel {
    private String imageUrl;
    private String option;

    public OptionsModel() {
    }

    public OptionsModel(String imageUrl, String option) {
        this.imageUrl = imageUrl;
        this.option = option;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
