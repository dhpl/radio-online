package com.philong.radioonline.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Long on 6/30/2017.
 */

public class Stream {

    @SerializedName("stream")
    private String mStream;
    @SerializedName("bitrate")
    private int mBitrate;
    @SerializedName("content_type")
    private String mContentType;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("listeners")
    private int mListener;


    public Stream(String stream, int bitrate, String contentType, int status, int listener) {
        mStream = stream;
        mBitrate = bitrate;
        mContentType = contentType;
        mStatus = status;
        mListener = listener;
    }

    public String getStream() {
        return mStream;
    }

    public void setStream(String stream) {
        mStream = stream;
    }

    public int getBitrate() {
        return mBitrate;
    }

    public void setBitrate(int bitrate) {
        mBitrate = bitrate;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String contentType) {
        mContentType = contentType;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int status) {
        mStatus = status;
    }

    public int getListener() {
        return mListener;
    }

    public void setListener(int listener) {
        mListener = listener;
    }
}
