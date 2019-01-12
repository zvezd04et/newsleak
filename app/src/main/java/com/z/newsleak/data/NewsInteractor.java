package com.z.newsleak.data;

import com.z.newsleak.data.api.NYTimesApi;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.NetworkUtils;
import com.z.newsleak.utils.NewsTypeConverters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsInteractor {

    private static final int TIMEOUT_IN_MINUTES = 1;

    @NonNull
    private NetworkUtils networkUtils;
    @NonNull
    private NYTimesApi api;
    @NonNull
    private NewsRepository repository;

    @Inject
    public NewsInteractor(@NonNull NetworkUtils networkUtils,
                          @NonNull NYTimesApi api,
                          @NonNull NewsRepository repository) {
        this.api = api;
        this.repository = repository;
        this.networkUtils = networkUtils;
    }

    public Observable<List<NewsItem>> getDataObservable() {
        return repository.getDataObservable();
    }

    public Completable loadNews(Category currentCategory) {
        return networkUtils.getOnlineNetwork()
                .timeout(TIMEOUT_IN_MINUTES, TimeUnit.MINUTES)
                .flatMapCompletable(aBoolean -> updateNews(currentCategory));
    }

    private Completable updateNews(Category currentCategory) {
        return api.getNews(currentCategory.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(repository::saveData);
    }
}
