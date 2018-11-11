package com.z.newsleak.model.network;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class NewsItemNetwork {

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
    private Date publishedDate;

    @SerializedName("multimedia")
    @Nullable
    private List<ImageNetwork> multimedia;

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
    public Date getPublishedDate() {
        return publishedDate;
    }

    @Nullable
    public List<ImageNetwork> getMultimedia() {
        return multimedia;
    }
}
