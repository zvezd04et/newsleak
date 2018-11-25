package com.z.newsleak.features.base;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;

public interface BaseNewsItemView extends MvpView {

    void setData(@NonNull NewsItem newsItem);

    void close();

}
