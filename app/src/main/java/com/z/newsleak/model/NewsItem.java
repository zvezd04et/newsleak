package com.z.newsleak.model;

import com.z.newsleak.utils.NewsItemConverter;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "news")
@TypeConverters(NewsItemConverter.class)
public class NewsItem implements Serializable {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    @NonNull
    private Category category;

    @ColumnInfo(name = "section")
    @Nullable
    private String section;

    @ColumnInfo(name = "title")
    @Nullable
    private String title;

    @ColumnInfo(name = "preview_text")
    @Nullable
    private String previewText;

    @ColumnInfo(name = "url")
    @Nullable
    private String url;

    @ColumnInfo(name = "published_date")
    @Nullable
    private Date publishedDate;

    @ColumnInfo(name = "normal_image_url")
    @Nullable
    private String normalImageUrl;

    @ColumnInfo(name = "large_image_url")
    @Nullable
    private String largeImageUrl;

    public NewsItem() {
    }

    public int getId() {
        return id;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    @Nullable
    public String getSection() {
        return section;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getPreviewText() {
        return previewText;
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
    public String getNormalImageUrl() {
        return normalImageUrl;
    }

    @Nullable
    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(@NonNull Category category) {
        this.category = category;
    }

    public void setSection(@Nullable String section) {
        this.section = section;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public void setPreviewText(@Nullable String previewText) {
        this.previewText = previewText;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }

    public void setPublishedDate(@Nullable Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setNormalImageUrl(@Nullable String normalImageUrl) {
        this.normalImageUrl = normalImageUrl;
    }

    public void setLargeImageUrl(@Nullable String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    //builder
    private NewsItem(Builder builder) {
        this.id = builder.id;
        this.category = builder.category;
        this.section = builder.section;
        this.title = builder.title;
        this.previewText = builder.previewText;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.normalImageUrl = builder.normalImageUrl;
        this.largeImageUrl = builder.largeImageUrl;
    }

    public static class Builder {

        private int id;
        @NonNull
        private Category category;
        @Nullable
        private String section;
        @Nullable
        private String title;
        @Nullable
        private String previewText;
        @Nullable
        private String url;
        @Nullable
        private Date publishedDate;
        @Nullable
        private String normalImageUrl;
        @Nullable
        private String largeImageUrl;

        public Builder(@NonNull Category category) {
            this.category = category;
        }

        @NonNull
        public Builder id(int id) {
            this.id = id;
            return this;
        }

        @NonNull
        public Builder section(@Nullable String section) {
            this.section = section;
            return this;
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
        public Builder largeImageUrl(@Nullable String largeImageUrl) {
            this.largeImageUrl = largeImageUrl;
            return this;
        }

        @NonNull
        public NewsItem build() {
            return new NewsItem(this);
        }
    }
}
