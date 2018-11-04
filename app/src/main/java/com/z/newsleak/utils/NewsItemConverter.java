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

public class NewsItemConverter {

    public static List<NewsItem> convertFromDtos(List<NewsItemDTO> newsItemDTOs) {

        List<NewsItem> news = new ArrayList<>();

        for (NewsItemDTO newsItemDTO:newsItemDTOs){
            String previewImageUrl = null;

            if (newsItemDTO.getMultimedia() != null && newsItemDTO.getMultimedia().size() != 0) {
                for (ImageDTO imageDTO:newsItemDTO.getMultimedia()){
                    if ("Normal".equals(imageDTO.getFormat())) {
                        previewImageUrl = imageDTO.getUrl();
                        break;
                    }
                }
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

            Category category;
            try {
                category = Category.valueOf(newsItemDTO.getSection().toUpperCase());
            } catch (IllegalArgumentException e) {
                category = Category.HOME;
            }

            Date date;
            try {
                date = formatter.parse(newsItemDTO.getPublishedDate());
                news.add(new NewsItem(newsItemDTO.getTitle(), previewImageUrl, category, newsItemDTO.getSection(),
                        date, newsItemDTO.getAbstractField(), newsItemDTO.getUrl()));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return news;
    }

    private NewsItemConverter() {
        throw new IllegalAccessError("Attempt to instantiate utility class.");
    }
}
