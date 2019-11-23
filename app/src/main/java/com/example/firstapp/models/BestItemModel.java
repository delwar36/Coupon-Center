package com.example.firstapp.models;

public class BestItemModel {
    private String thumbnail;

    private String category;

    private String title;

    private String url;

    private String offer;

    public BestItemModel(){

    }



    public BestItemModel(String thumbnail, String category, String title, String url, String offer) {
        this.thumbnail = thumbnail;
        this.category = category;
        this.title = title;
        this.url = url;
        this.offer = offer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

}