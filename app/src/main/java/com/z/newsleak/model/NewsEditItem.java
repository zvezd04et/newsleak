package com.z.newsleak.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsEditItem {

    @Nullable
    private String title;
    @Nullable
    private String previewText;
    @Nullable
    private String url;
    @Nullable
    private String normalImageUrl;
    @Nullable
    private Date publishedDate;

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(@Nullable String previewText) {
        this.previewText = previewText;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getNormalImageUrl() {
        return normalImageUrl;
    }

    public void setNormalImageUrl(@Nullable String normalImageUrl) {
        this.normalImageUrl = normalImageUrl;
    }

    @Nullable
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(@Nullable Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    //builder
    private NewsEditItem(Builder builder) {
        this.title = builder.title;
        this.previewText = builder.previewText;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.normalImageUrl = builder.normalImageUrl;
    }
    public static class Builder {

        private String title;
        @Nullable
        private String previewText;
        @Nullable
        private String url;
        @Nullable
        private Date publishedDate;
        @Nullable
        private String normalImageUrl;

        public Builder() {
        }

        @NonNull
        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        @NonNull
        public Builder previewText(@Nullable String previewText) {
            this.previewText = previewText;
            return this;
        }

        @NonNull
        public Builder url(@Nullable String url) {
            this.url = url;
            return this;
        }

        @NonNull
        public Builder publishedDate(@Nullable Date publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        @NonNull
        public Builder normalImageUrl(@Nullable String normalImageUrl) {
            this.normalImageUrl = normalImageUrl;
            return this;
        }

        @NonNull
        public NewsEditItem build() {
            return new NewsEditItem(this);
        }
    }
}
