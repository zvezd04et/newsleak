package com.z.newsleak.model.db;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_item")
public class NewsEntity {

    @ColumnInfo(name = "id")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "section")
    @Nullable
    private String section;

    @ColumnInfo(name = "title")
    @Nullable
    private String title;

    @ColumnInfo(name = "abstract")
    @Nullable
    private String abstractField;

    @ColumnInfo(name = "url")
    @Nullable
    private String url;

    @ColumnInfo(name = "published_date")
    @Nullable
    private String publishedDate;

    @ColumnInfo(name = "normal_image_url")
    @Nullable
    private String normalImageUrl;

    public int getId() {
        return id;
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
    public String getNormalImageUrl() {
        return normalImageUrl;
    }

    //builder
    public NewsEntity(Builder builder) {
        this.id = builder.id;
        this.section = builder.section;
        this.title = builder.title;
        this.abstractField = builder.abstractField;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.normalImageUrl = builder.normalImageUrl;
    }

    public static class Builder {

        private int id;
        @Nullable
        private String section;
        @Nullable
        private String title;
        @Nullable
        private String abstractField;
        @Nullable
        private String url;
        @Nullable
        private String publishedDate;
        @Nullable
        private String normalImageUrl;

        public Builder(int id) {
            this.id = id;
        }

        public Builder section(@Nullable String section) {
            this.section = section;
            return this;
        }

        public Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        public Builder abstractField(@Nullable String abstractField) {
            this.abstractField = abstractField;
            return this;
        }

        public Builder url(@Nullable String url) {
            this.url = url;
            return this;
        }

        public Builder publishedDate(@Nullable String publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder normalImageUrl(@Nullable String normalImageUrl) {
            this.normalImageUrl = normalImageUrl;
            return this;
        }

        public NewsEntity build() {
            return new NewsEntity(this);
        }
    }
}
