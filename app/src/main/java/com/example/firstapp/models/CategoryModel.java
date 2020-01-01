package com.example.firstapp.models;

public class CategoryModel {

    private String title;
    private String category;
    private String thumbnail;

    public CategoryModel(){

    }

    public CategoryModel(String title, String category, String thumbnail) {
        this.title = title;
        this.category = category;
        this.thumbnail = thumbnail;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}