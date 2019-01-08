package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.api.NYTimesApi;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.features.base.BasePresenter;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.utils.NewsTypeConverters;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String LOG_TAG = "NewsListPresenter";

    @NonNull
    private Category currentCategory;
    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();
    @NonNull
    private NewsRepository repository;
    @NonNull
    private PreferencesManager preferencesManager;
    @NonNull
    private NYTimesApi api;

    @Inject
    public NewsListPresenter(@NonNull NYTimesApi api,
                             @NonNull NewsRepository repository,
                             @NonNull PreferencesManager preferencesManager) {
        this.repository = repository;
        this.preferencesManager = preferencesManager;
        this.api = api;

        currentCategory = preferencesManager.getCurrentCategory();
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        final Disposable disposable = repository.getDataObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processNews,
                        this::handleError);

        compositeDisposable.add(disposable);
    }

    public void loadNews(@NonNull Category category) {
        getViewState().showState(LoadState.LOADING);

        final Disposable disposable = api
                .getNews(category.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(repository::saveData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().showNews(newsList),
                        this::handleError);

        compositeDisposable.add(disposable);
    }

    public void onSetupSpinnerSelection() {
        getViewState().setSpinnerSelection(currentCategory.ordinal());
    }

    public void onSpinnerCategorySelected(@Nullable Category category) {
        if (category == null) {
            return;
        }
        if (category.equals(currentCategory)) {
            return;
        }
        loadNews(category);
        currentCategory = category;
        preferencesManager.setCurrentCategory(currentCategory);
    }

    private void processNews(@Nullable List<NewsItem> news) {
        if (news == null || news.isEmpty()) {
            getViewState().showState(LoadState.HAS_NO_DATA);
            return;
        }

        newsList = news;
        getViewState().showNews(newsList);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        getViewState().showState(LoadState.ERROR);
    }
}
