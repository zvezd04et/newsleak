package com.z.newsleak.features.news_details;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.features.base.BaseNewsItemView;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;

public interface NewsDetailsContract {

    interface View extends BaseNewsItemView {

    }

    interface Presenter extends MvpPresenter<NewsDetailsContract.View> {

        void deleteData();

    }
}
