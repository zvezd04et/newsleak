package com.z.newsleak.utils;

import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.model.network.ImageNetwork;
import com.z.newsleak.model.network.NewsItemNetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItemConverter {

    private final static String IMAGE_FORMAT = "Normal";
    private final static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    @NonNull
    public static List<NewsItem> convertFromNetwork(@NonNull List<NewsItemNetwork> newsItemsNetwork, @Nullable Category currentCategory) {

        final List<NewsItem> news = new ArrayList<>();

        for (NewsItemNetwork newsItemNetwork : newsItemsNetwork) {
            final String previewImageUrl = getImageUrl(newsItemNetwork.getMultimedia());
            final Category category = getCategory(newsItemNetwork.getSection(), currentCategory);
            final Date publishDate = getPublishDate(newsItemNetwork.getPublishedDate());

            final NewsItem newsItem = new NewsItem.Builder(category)
                    .section(newsItemNetwork.getSection())
                    .title(newsItemNetwork.getTitle())
                    .imageUrl(previewImageUrl)
                    .previewText(newsItemNetwork.getAbstractField())
                    .publishDate(publishDate)
                    .articleUrl(newsItemNetwork.getUrl())
                    .build();
            news.add(newsItem);
        }

        return news;
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

    @Nullable
    private static Date getPublishDate(@Nullable String publishDate) {

        final SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(publishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;

    }

    private NewsItemConverter() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
