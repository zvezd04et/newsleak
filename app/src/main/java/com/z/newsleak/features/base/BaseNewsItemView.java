package com.z.newsleak.features.base;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;

public interface BaseNewsItemView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setData(@NonNull NewsItem newsItem);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void close();
}
