package com.z.newsleak.features.newsfeed;


import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.network.NewsResponse;

import retrofit2.Response;

public interface NewsListView extends MvpView {

    void showLoading();

    void processResponse(Response<NewsResponse> response);

    void handleError(Throwable th);
}
