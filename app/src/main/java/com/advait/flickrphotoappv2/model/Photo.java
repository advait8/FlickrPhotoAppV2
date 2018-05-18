package com.advait.flickrphotoappv2.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Photo implements Parcelable{
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

    protected Photo(Parcel in) {
        id = in.readInt();
        title = in.readString();
        urlString = in.readString();
        height = in.readInt();
        width = in.readInt();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(urlString);
        dest.writeInt(height);
        dest.writeInt(width);
    }
}
