package com.z.newsleak.model.db;

import com.z.newsleak.model.Category;
import com.z.newsleak.utils.NewsItemConverter;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "news")
@TypeConverters(NewsItemConverter.class)
public class NewsEntity {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "section")
    @Nullable
    private String section;

    @ColumnInfo(name = "title")
    @Nullable
    private String title;

    @ColumnInfo(name = "abstract")
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

    @ColumnInfo(name = "category")
    @NonNull
    private Category category;

    public NewsEntity() {
    }

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

    @NonNull
    public Category getCategory() {
        return category;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setCategory(@NonNull Category category) {
        this.category = category;
    }

    //builder
    @Ignore
    public NewsEntity(Builder builder) {
        this.section = builder.section;
        this.title = builder.title;
        this.previewText = builder.abstractField;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.normalImageUrl = builder.normalImageUrl;
        this.category = builder.category;
    }

    public static class Builder {

        @Nullable
        private String section;
        @Nullable
        private String title;
        @Nullable
        private String abstractField;
        @Nullable
        private String url;
        @Nullable
        private Date publishedDate;
        @Nullable
        private String normalImageUrl;
        @NonNull
        private Category category;

        public Builder(@NonNull Category category) {
            this.category = category;
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

        public Builder publishedDate(@Nullable Date publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder normalImageUrl(@Nullable String normalImageUrl) {
            this.normalImageUrl = normalImageUrl;
            return this;
        }

        public Builder category(@NonNull Category category) {
            this.category = category;
            return this;
        }

        public NewsEntity build() {
            return new NewsEntity(this);
        }
    }
}
