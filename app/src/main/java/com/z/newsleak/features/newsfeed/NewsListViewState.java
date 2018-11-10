package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import com.z.newsleak.data.LoadState;
import com.z.newsleak.network.NewsResponse;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Response;

public class NewsListViewState implements ViewState<NewsListContract.View> {

    private final String LOG_TAG = "NewsListViewState";

    @NonNull
    private LoadState state = LoadState.HAS_NO_DATA;

    @Nullable
    private Response<NewsResponse> response;

    @Override
    public void apply(@NonNull NewsListContract.View view, boolean retained) {

        switch (state) {
            case HAS_DATA:
                view.processResponse(response);
                break;
            case HAS_NO_DATA:
                break;
            case NETWORK_ERROR:
                view.showError(state);
                break;
            case SERVER_ERROR:
                view.showError(state);
                break;
            case LOADING:
                view.showLoading();
                break;

            default:
                Log.d(LOG_TAG, "Unknown state: " + state);
        }

    }

    public void setSwowLoading() {
        state = LoadState.LOADING;
    }

    public void setSwowError(LoadState stateError) {
        state = stateError;
        response = null;
    }

    public void setResponse(Response<NewsResponse> response) {
        this.response = response;
        state = LoadState.HAS_DATA;
    }
}