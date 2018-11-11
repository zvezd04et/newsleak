package com.z.newsleak.features.newsfeed;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsListViewState implements ViewState<NewsListContract.View> {

    @NonNull
    private LoadState state = LoadState.HAS_NO_DATA;

    @Nullable
    private List<NewsItem> news;

    @Override
    public void apply(@NonNull NewsListContract.View view, boolean retained) {

        if (news == null) {
            view.showState(state);
        } else {
            view.showNews(news);
        }
    }

    public void setState(@NonNull LoadState state) {
        this.state = state;
        news = null;
    }

    public void setNews(@NonNull List<NewsItem> news) {
        this.news = news;
        state = LoadState.HAS_DATA;
    }
}