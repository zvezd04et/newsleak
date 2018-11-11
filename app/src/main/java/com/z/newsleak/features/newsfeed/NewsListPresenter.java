package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.data.api.NYTimesApiProvider;
import com.z.newsleak.utils.NewsItemConverter;
import com.z.newsleak.utils.SupportUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends MvpBasePresenter<NewsListContract.View> implements NewsListContract.Presenter {

    private static final String LOG_TAG = "NewsListPresenter";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    private Category currentCategory = null;

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

    @Override
    public void loadNews(@NonNull Category category) {

        final Disposable searchDisposable = NYTimesApiProvider.getInstance()
                .createApi()
                .getNews(category.getSection())
                .map(response -> NewsItemConverter.convertFromNetwork(response.getResults(), currentCategory))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> showViewState(LoadState.LOADING))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processNews,
                        this::handleError);
        compositeDisposable.add(searchDisposable);
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
    }

    private void processNews(@Nullable List<NewsItem> news) {

        if (news == null || news.isEmpty()) {
            showViewState(LoadState.HAS_NO_DATA);
            return;
        }

        ifViewAttached(view -> view.showNews(news));
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        showViewState(LoadState.ERROR);
    }

    private void showViewState(LoadState state) {
        ifViewAttached(view -> view.showState(state));
    }
}
