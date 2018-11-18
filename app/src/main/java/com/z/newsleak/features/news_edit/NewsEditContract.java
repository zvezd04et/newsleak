package com.z.newsleak.features.news_edit;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.model.NewsItem;

import java.util.Calendar;

import androidx.annotation.NonNull;

public interface NewsEditContract {

    interface View extends MvpView {

        void setCalendar(@NonNull Calendar calendar);

        void setData(@NonNull NewsItem newsItem);

        void updateData(@NonNull NewsItem newsItem);

        void close();
    }

    interface Presenter extends MvpPresenter<NewsEditContract.View> {

        void getData(int id);

        void saveData();
    }
}
