package com.z.newsleak.features.newsfeed;

import android.util.Log;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.model.network.NewsResponse;
import com.z.newsleak.model.network.NewsItemNetwork;
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
import retrofit2.Response;

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
        if (category.equals(currentCategory)) {
            return;
        }

        final Disposable searchDisposable = NYTimesApiProvider.getInstance()
                .createApi()
                .getNews(category.getSection())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> showViewState(LoadState.LOADING))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResponse,
                        this::handleError);
        compositeDisposable.add(searchDisposable);

        currentCategory = category;
    }

    private void processResponse(@NonNull Response<NewsResponse> response) {

        if (!response.isSuccessful()) {
            showViewState(LoadState.SERVER_ERROR);
            return;
        }

        final NewsResponse body = response.body();
        if (body == null) {
            showViewState(LoadState.HAS_NO_DATA);
            return;
        }

        final List<NewsItemNetwork> newsItemsNetwork = body.getResults();
        if (newsItemsNetwork == null || newsItemsNetwork.isEmpty()) {
            showViewState(LoadState.HAS_NO_DATA);
            return;
        }

        List<NewsItem> news = NewsItemConverter.convertFromNetwork(newsItemsNetwork, currentCategory);
        ifViewAttached(view -> view.showNews(news));
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        showViewState(LoadState.NETWORK_ERROR);
    }

    private void showViewState(LoadState state) {
        ifViewAttached(view -> view.showState(state));
    }
}
