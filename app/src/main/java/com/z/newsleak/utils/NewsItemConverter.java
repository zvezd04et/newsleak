package com.z.newsleak.utils;

import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.model.db.NewsEntity;
import com.z.newsleak.model.network.ImageNetwork;
import com.z.newsleak.model.network.NewsItemNetwork;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItemConverter {

    private final static String IMAGE_FORMAT = "Normal";

    @Nullable
    public static List<NewsItem> convertFromNetwork(@Nullable List<NewsItemNetwork> newsItemsNetwork, @Nullable Category currentCategory) {

        if (newsItemsNetwork == null) {
            return null;
        }

        final List<NewsItem> news = new ArrayList<>(newsItemsNetwork.size());

        for (NewsItemNetwork newsItemNetwork : newsItemsNetwork) {
            final String previewImageUrl = getImageUrl(newsItemNetwork.getMultimedia());
            final Category category = getCategory(newsItemNetwork.getSection(), currentCategory);

            final NewsItem newsItem = new NewsItem.Builder(category)
                    .section(newsItemNetwork.getSection())
                    .title(newsItemNetwork.getTitle())
                    .imageUrl(previewImageUrl)
                    .previewText(newsItemNetwork.getAbstractField())
                    .publishDate(newsItemNetwork.getPublishedDate())
                    .articleUrl(newsItemNetwork.getUrl())
                    .build();
            news.add(newsItem);
        }

        return news;
    }

    @Nullable
    public static List<NewsItem> convertFromDb(@Nullable List<NewsEntity> newsEntities, @Nullable Category currentCategory) {

        if (newsEntities == null) {
            return null;
        }

        final List<NewsItem> news = new ArrayList<>(newsEntities.size());
        for (NewsEntity newsEntity : newsEntities) {
            final Category category = getCategory(newsEntity.getSection(), currentCategory);

            final NewsItem newsItem = new NewsItem.Builder(category)
                    .section(newsEntity.getSection())
                    .title(newsEntity.getTitle())
                    .imageUrl(newsEntity.getNormalImageUrl())
                    .previewText(newsEntity.getPreviewText())
                    .publishDate(newsEntity.getPublishedDate())
                    .articleUrl(newsEntity.getUrl())
                    .id(newsEntity.getId())
                    .build();
            news.add(newsItem);
        }

        return news;
    }

    @Nullable
    public static List<NewsEntity> convertFromNetworkToDb(@Nullable List<NewsItemNetwork> newsItemsNetwork, @Nullable Category currentCategory) {

        if (newsItemsNetwork == null) {
            return null;
        }

        final List<NewsEntity> newsEntities = new ArrayList<>(newsItemsNetwork.size());

        for (NewsItemNetwork newsItemNetwork : newsItemsNetwork) {

            final Category category = getCategory(newsItemNetwork.getSection(), currentCategory);

            final String normalImageUrl = getImageUrl(newsItemNetwork.getMultimedia());
            final NewsEntity newsEntity = new NewsEntity.Builder(category)
                    .section(newsItemNetwork.getSection())
                    .title(newsItemNetwork.getTitle())
                    .normalImageUrl(normalImageUrl)
                    .abstractField(newsItemNetwork.getAbstractField())
                    .publishedDate(newsItemNetwork.getPublishedDate())
                    .url(newsItemNetwork.getUrl())
                    .build();

            newsEntities.add(newsEntity);
        }

        return newsEntities;
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
    private static Category getCategory(@Nullable String section, @Nullable Category currentCategory) {

        if (section == null) {
            return Category.HOME;
        }

        for (Category category : Category.values()) {
            if (section.equals(category.getSection())) {
                return category;
            }
        }

        return (currentCategory != null) ? currentCategory : Category.HOME;
    }

    private NewsItemConverter() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
