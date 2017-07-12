package com.philong.radioonline.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Long on 6/30/2017.
 */

public class Image {

    @SerializedName("url")
    private String mUrl;
    @SerializedName("thumb")
    private Thumb mThumb;

    public Image(String url, Thumb thumb) {
        mUrl = url;
        mThumb = thumb;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public Thumb getThumb() {
        return mThumb;
    }

    public void setThumb(Thumb thumb) {
        mThumb = thumb;
    }
}
