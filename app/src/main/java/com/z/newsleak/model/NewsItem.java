package com.z.newsleak.model;

import com.z.newsleak.data.Category;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItem implements Serializable {

    @Nullable
    private final String title;
    @Nullable
    private final String imageUrl;
    @NonNull
    private final Category category;
    @Nullable
    private final String section;
    @Nullable
    private final Date publishDate;
    @Nullable
    private final String previewText;
    @Nullable
    private final String articleUrl;

    public NewsItem(@Nullable String title, @Nullable String imageUrl, @NonNull Category category,
                    @Nullable String section, @Nullable Date publishDate, @Nullable String previewText,
                    @Nullable String articleUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.section = section;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.articleUrl = articleUrl;
        this.category = category;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    @Nullable
    public Date getPublishDate() {
        return publishDate;
    }

    @Nullable
    public String getPreviewText() {
        return previewText;
    }

    @Nullable
    public String getArticleUrl() {
        return articleUrl;
    }

    @Nullable
    public String getSection() {
        return section;
    }

    //builder
    private NewsItem(Builder builder) {
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.section = builder.section;
        this.publishDate = builder.publishDate;
        this.previewText = builder.previewText;
        this.articleUrl = builder.articleUrl;
        this.category = builder.category;
    }

    public static class Builder {

        @NonNull
        private Category category;

        @Nullable
        private String title;
        @Nullable
        private String imageUrl;
        @Nullable
        private String section;
        @Nullable
        private Date publishDate;
        @Nullable
        private String previewText;
        @Nullable
        private String articleUrl;

        public Builder(@NonNull Category category) {
            this.category = category;
        }

        @NonNull
        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        @NonNull
        public Builder imageUrl(@Nullable String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        @NonNull
        public Builder section(@Nullable String section) {
            this.section = section;
            return this;
        }

        @NonNull
        public Builder publishDate(@Nullable Date publishDate) {
            this.publishDate = publishDate;
            return this;
        }

        @NonNull
        public Builder previewText(@Nullable String previewText) {
            this.previewText = previewText;
            return this;
        }

        @NonNull
        public Builder articleUrl(@Nullable String articleUrl) {
            this.articleUrl = articleUrl;
            return this;
        }

        @NonNull
        public NewsItem build() {
            return new NewsItem(this);
        }
    }
}
