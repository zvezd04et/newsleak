package com.z.newsleak.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.Nullable;

public class NewsItemDTO {

    @SerializedName("section")
    @Nullable
    private String section;

    @SerializedName("subsection")
    @Nullable
    private String subsection;

    @SerializedName("title")
    @Nullable
    private String title;

    @SerializedName("abstract")
    @Nullable
    private String abstractField;

    @SerializedName("url")
    @Nullable
    private String url;

    @SerializedName("published_date")
    @Nullable
    private String publishedDate;

    @SerializedName("multimedia")
    @Nullable
    private List<ImageDTO> multimedia;

    @Nullable
    public String getSection() {
        return section;
    }

    @Nullable
    public String getSubsection() {
        return subsection;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getAbstractField() {
        return abstractField;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getPublishedDate() {
        return publishedDate;
    }

    @Nullable
    public List<ImageDTO> getMultimedia() {
        return multimedia;
    }
}
