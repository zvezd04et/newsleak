package com.z.newsleak.features.newsfeed;

import com.z.newsleak.model.NewsItem;

import java.util.List;

import androidx.annotation.Nullable;

public interface NewsListView {

    void loadNews();

    void updateNews(@Nullable List<NewsItem> news);

    void showProgress(boolean shouldShow);

}
