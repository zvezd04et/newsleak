package com.z.newsleak.model.db;

import com.z.newsleak.data.db.DateConverter;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "news")
@TypeConverters(DateConverter.class)
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
    private String abstractField;

    @ColumnInfo(name = "url")
    @Nullable
    private String url;

    @ColumnInfo(name = "published_date")
    @Nullable
    private Date publishedDate;

    @ColumnInfo(name = "normal_image_url")
    @Nullable
    private String normalImageUrl;

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
    public String getNormalImageUrl() {
        return normalImageUrl;
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

    public void setAbstractField(@Nullable String abstractField) {
        this.abstractField = abstractField;
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

    //builder
    @Ignore
    public NewsEntity(Builder builder) {
        this.section = builder.section;
        this.title = builder.title;
        this.abstractField = builder.abstractField;
        this.url = builder.url;
        this.publishedDate = builder.publishedDate;
        this.normalImageUrl = builder.normalImageUrl;
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

        public Builder() {

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

        public NewsEntity build() {
            return new NewsEntity(this);
        }
    }
}
