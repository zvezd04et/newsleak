package com.z.newsleak.features.news_edit;

import com.z.newsleak.features.base.BaseNewsItemPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsEditPresenter extends BaseNewsItemPresenter<NewsEditContract.View> implements NewsEditContract.Presenter {

    public NewsEditPresenter(int id) {
        super(id);
    }

    @Override
    public void saveData() {

        if (newsItem == null) {
            return;
        }

        ifViewAttached(view -> view.updateData(newsItem));

        final Disposable disposable = database.update(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processSaving, this::handleError);
        compositeDisposable.add(disposable);
    }


    private void processSaving() {
        ifViewAttached(NewsEditContract.View::close);
    }

}
