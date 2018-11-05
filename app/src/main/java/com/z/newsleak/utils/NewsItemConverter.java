package com.z.newsleak.utils;

import com.z.newsleak.data.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.network.dto.ImageDTO;
import com.z.newsleak.network.dto.NewsItemDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsItemConverter {

    public static List<NewsItem> convertFromDtos(@NonNull List<NewsItemDTO> newsItemDTOs, @Nullable Category currentCategory) {

        final List<NewsItem> news = new ArrayList<>();

        for (NewsItemDTO newsItemDTO : newsItemDTOs) {
            final String previewImageUrl = getImageUrl(newsItemDTO.getMultimedia());
            final Category category = getCategory(newsItemDTO.getSection(), currentCategory);
            final Date publishDate = getPublishDate(newsItemDTO.getPublishedDate());

            news.add(new NewsItem(newsItemDTO.getTitle(), previewImageUrl, category, newsItemDTO.getSection(),
                    publishDate, newsItemDTO.getAbstractField(), newsItemDTO.getUrl()));
        }

        return news;
    }

    @Nullable
    private static String getImageUrl(@Nullable List<ImageDTO> multimedia) {

        if (multimedia == null) {
            return null;
        }

        if (multimedia.size() == 0) {
            return null;
        }

        String previewImageUrl = null;
        for (ImageDTO imageDTO : multimedia) {
            if ("Normal".equals(imageDTO.getFormat())) {
                previewImageUrl = imageDTO.getUrl();
                break;
            }
        }
        return previewImageUrl;
    }

    @NonNull
    private static Category getCategory(@Nullable String section, @Nullable Category currentCategory) {

        if (section==null){
            return Category.HOME;
        }

        for (Category category: Category.values()){
            if (section.equals(category.getSection())) {
                return category;
            }
        }

        return (currentCategory != null) ? currentCategory : Category.HOME;
    }

    @Nullable
    private static Date getPublishDate(@Nullable String publishDate) {

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
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
