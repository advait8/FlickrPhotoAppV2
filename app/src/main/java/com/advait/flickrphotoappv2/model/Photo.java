package com.advait.flickrphotoappv2.model;

import org.json.JSONObject;

public class Photo {
    private int id;
    private String title;
    private String urlString;
    private int height;
    private int width;

    public Photo(int id, String title, String urlString, int height, int width) {
        this.id= id;
        this.title=  title;
        this.urlString =urlString;
        this.height = height;
        this.width = width;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }


    public String getUrlString() {
        return urlString;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
