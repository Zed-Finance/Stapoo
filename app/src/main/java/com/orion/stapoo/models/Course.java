package com.orion.stapoo.models;

public class Course {
    String title;
    String materialId;

    public Course() {
    }

    public Course(String title, String materialId) {
        this.title = title;
        this.materialId = materialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }
}
