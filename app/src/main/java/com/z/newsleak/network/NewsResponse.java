package com.z.newsleak.network;

import com.google.gson.annotations.SerializedName;
import com.z.newsleak.network.dto.NewsItemNetwork;

import java.util.List;

import androidx.annotation.Nullable;

public class NewsResponse {

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
    private List<NewsItemNetwork> results;

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
    public List<NewsItemNetwork> getResults() {
        return results;
    }
}
