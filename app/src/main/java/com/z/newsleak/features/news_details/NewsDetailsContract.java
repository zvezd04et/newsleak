package com.z.newsleak.features.news_details;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;

public interface NewsDetailsContract {

    interface View extends MvpView {

        void setData(@NonNull NewsItem newsItem);

        void close();
    }

    interface Presenter extends MvpPresenter<NewsDetailsContract.View> {

        void getData(int id);

        void deleteData();
    }
}
