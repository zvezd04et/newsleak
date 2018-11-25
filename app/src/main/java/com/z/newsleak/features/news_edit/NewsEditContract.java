package com.z.newsleak.features.news_edit;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.features.base.BaseNewsItemView;
import com.z.newsleak.model.NewsItem;

import java.util.Calendar;

import androidx.annotation.NonNull;

public interface NewsEditContract {

    interface View extends BaseNewsItemView {

        void setCalendar(@NonNull Calendar calendar);

        void updateData(@NonNull NewsItem newsItem);

    }

    interface Presenter extends MvpPresenter<NewsEditContract.View> {

        void saveData();

    }
}
