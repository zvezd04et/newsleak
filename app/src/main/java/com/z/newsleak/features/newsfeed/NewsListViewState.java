package com.z.newsleak.features.newsfeed;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import com.z.newsleak.ui.LoadState;
import com.z.newsleak.network.NewsResponse;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Response;

public class NewsListViewState implements ViewState<NewsListContract.View> {

    @NonNull
    private LoadState state = LoadState.HAS_NO_DATA;

    @Nullable
    private Response<NewsResponse> response;

    @Override
    public void apply(@NonNull NewsListContract.View view, boolean retained) {

        if (response == null) {
            view.showState(state);
        } else {
            view.processResponse(response);
        }

    }

    public void setState(@NonNull LoadState state) {
        this.state = state;
        response = null;
    }

    public void setResponse(@NonNull Response<NewsResponse> response) {
        this.response = response;
        state = LoadState.HAS_DATA;
    }
}