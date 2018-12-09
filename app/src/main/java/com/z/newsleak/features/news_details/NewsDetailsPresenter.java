package com.z.newsleak.features.news_details;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.App;
import com.z.newsleak.data.db.NewsRepository;
import com.z.newsleak.features.base.BaseNewsItemPresenter;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsDetailsPresenter extends BaseNewsItemPresenter<NewsDetailsView> {

    public NewsDetailsPresenter(int id) {
        super(id);
    }

    public void onDeleteSelected() {
        Disposable disposable = repository.deleteItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processDeleting, this::handleError);
        compositeDisposable.add(disposable);
    }

    public void onEditSelected() {
        getViewState().openEditorActivity(id);
    }

    private void processDeleting() {
        getViewState().close();
    }
}
