package com.z.newsleak.features.base;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends MvpView> extends MvpPresenter<V> {

    @NonNull
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    protected final NewsDao database = App.getDatabase().getNewsDao();

    @Override
    public void onDestroy() {
        super.onDestroy();
        SupportUtils.disposeSafely(compositeDisposable);
    }

}
