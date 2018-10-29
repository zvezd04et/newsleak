package com.z.newsleak.network.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.Nullable;

public class NewsDTO {

    @SerializedName("status")
    @Nullable
    private String status;

    @SerializedName("section")
    @Nullable
    private String section;

    @SerializedName("num_results")
    private int numResults;

    @SerializedName("results")
    @Nullable
    private List<NewsItemDTO> results;

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getSection() {
        return section;
    }

    public int getNumResults() {
        return numResults;
    }

    @Nullable
    public List<NewsItemDTO> getResults() {
        return results;
    }
}