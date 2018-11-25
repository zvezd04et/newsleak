package com.z.newsleak.features.base;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private boolean firstLaunch = true;

    @NonNull
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    protected final NewsDao database = App.getDatabase().getNewsDao();

    @Override
    public void attachView(V view) {
        super.attachView(view);

        if (firstLaunch) {
            firstLaunch = false;

            onFirstViewAttach();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

    protected void onFirstViewAttach() {

    }
}
