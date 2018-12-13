package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.App;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.data.api.NYTimesApiProvider;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.features.base.BasePresenter;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.utils.NewsTypeConverters;
import com.z.newsleak.utils.SupportUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String LOG_TAG = "NewsListPresenter";

    @Nullable
    private Disposable disposable;

    @Nullable
    private Category currentCategory = null;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @NonNull
    private NewsRepository repository = App.getRepository();

    @NonNull
    private final PreferencesManager preferencesManager = App.getPreferencesManager();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        currentCategory = preferencesManager.getCurrentCategory();
        getViewState().setupSpinner(currentCategory);

        final Disposable disposable = repository.getDataObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processNews,
                        this::handleError);

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SupportUtils.disposeSafely(disposable);
    }

    public void loadNews(@NonNull Category category) {
        getViewState().showState(LoadState.LOADING);

        disposable = NYTimesApiProvider.getInstance()
                .createApi()
                .getNews(category.getSection())
                .map(response -> NewsTypeConverters.convertFromNetworkToDb(response.getResults(), currentCategory))
                .flatMapCompletable(repository::saveData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().showNews(newsList),
                        this::handleError);
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
