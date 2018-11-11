package com.z.newsleak.network.dto;

import com.google.gson.annotations.SerializedName;

public class ImageNetwork {

    @SerializedName("url")
    private String url;
    @SerializedName("format")
    private String format;
    @SerializedName("height")
    private int height;
    @SerializedName("width")
    private int width;

    public String getUrl() {
        return url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getFormat() {
        return format;
    }
}
