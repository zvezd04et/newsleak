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
}
