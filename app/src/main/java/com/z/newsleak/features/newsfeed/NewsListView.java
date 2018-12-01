package com.z.newsleak.features.newsfeed;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;

import java.util.List;

import androidx.annotation.NonNull;

public interface NewsListView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showNews(@NonNull List<NewsItem> news);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showState(@NonNull LoadState state);

}