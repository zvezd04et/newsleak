package com.z.newsleak.utils;

import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.model.network.ImageNetwork;
import com.z.newsleak.model.network.NewsItemNetwork;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

public class NewsItemConverter {

    private final static String IMAGE_FORMAT = "Normal";

    @Nullable
    public static List<NewsItem> convertFromNetworkToDb(@Nullable List<NewsItemNetwork> newsItemsNetwork, @Nullable Category currentCategory) {

        if (newsItemsNetwork == null) {
            return null;
        }

        final List<NewsItem> newsEntities = new ArrayList<>(newsItemsNetwork.size());

        for (NewsItemNetwork newsItemNetwork : newsItemsNetwork) {

            final Category category = toCategory(newsItemNetwork.getSection(), currentCategory);
            final String normalImageUrl = getImageUrl(newsItemNetwork.getMultimedia());

            final NewsItem newsItem = new NewsItem.Builder(category)
                    .section(newsItemNetwork.getSection())
                    .title(newsItemNetwork.getTitle())
                    .normalImageUrl(normalImageUrl)
                    .previewText(newsItemNetwork.getAbstractField())
                    .publishedDate(newsItemNetwork.getPublishedDate())
                    .url(newsItemNetwork.getUrl())
                    .build();

            newsEntities.add(newsItem);
        }

        return newsEntities;
    }

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Category toCategory(@Nullable String section) {

        if (section == null) {
            return Category.HOME;
        }

        for (Category category : Category.values()) {
            if (section.equals(category.getSection())) {
                return category;
            }
        }

        return Category.HOME;
    }

    @TypeConverter
    public static String fromCategory(Category category) {
        return category == null ? null : category.getSection();
    }

    @Nullable
    private static String getImageUrl(@Nullable List<ImageNetwork> multimedia) {

        if (multimedia == null) {
            return null;
        }

        if (multimedia.size() == 0) {
            return null;
        }

        String previewImageUrl = null;
        for (ImageNetwork imageNetwork : multimedia) {
            if (IMAGE_FORMAT.equals(imageNetwork.getFormat())) {
                previewImageUrl = imageNetwork.getUrl();
                break;
            }
        }
        return previewImageUrl;
    }

    @NonNull
    private static Category toCategory(@Nullable String section, @Nullable Category currentCategory) {

        final Category category = toCategory(section);
        if (category != null) {
            return category;
        }

        return (currentCategory != null) ? currentCategory : Category.HOME;
    }

    private NewsItemConverter() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
