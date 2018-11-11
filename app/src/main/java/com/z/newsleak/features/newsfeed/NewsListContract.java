package com.z.newsleak.features.newsfeed;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.data.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.network.NewsResponse;

import java.util.List;

import androidx.annotation.NonNull;
import retrofit2.Response;

public interface NewsListContract {

    interface View extends MvpView {

        void showNews(List<NewsItem> news);

        void showState(@NonNull LoadState state);
    }

    interface Presenter extends MvpPresenter<View> {
        void loadNews(@NonNull Category category);
    }
}
