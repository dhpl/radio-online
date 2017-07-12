package com.philong.radioonline.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Long on 6/30/2017.
 */

public class Thumb {

    @SerializedName("url")
    private String mUrl;

    public Thumb(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
