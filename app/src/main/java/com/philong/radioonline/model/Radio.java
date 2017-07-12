package com.philong.radioonline.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Long on 6/30/2017.
 */

public class Radio {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("image")
    private Image mImage;
    @SerializedName("streams")
    private Stream[] mStream;

    public Radio(int id, String name, Image image, Stream[] stream) {
        mId = id;
        mName = name;
        mImage = image;
        mStream = stream;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public Stream[] getStream() {
        return mStream;
    }

    public void setStream(Stream[] stream) {
        mStream = stream;
    }
}
