package com.z.newsleak.network.dto;

import com.google.gson.annotations.SerializedName;

public class ImageDTO {

    @SerializedName("url")
    private String url;
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
}
