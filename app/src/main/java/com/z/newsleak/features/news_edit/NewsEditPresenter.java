package com.z.newsleak.features.news_edit;

import com.arellomobile.mvp.InjectViewState;
import com.z.newsleak.features.base.BaseNewsItemPresenter;
import com.z.newsleak.model.NewsEditItem;
import com.z.newsleak.model.NewsItem;

import java.util.Calendar;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class NewsEditPresenter extends BaseNewsItemPresenter<NewsEditView> {

    @NonNull
    private Calendar calendar = Calendar.getInstance();

    public NewsEditPresenter(int id) {
        super(id);
    }

    public void saveData(@NonNull NewsEditItem newsEditItem) {
        if (newsItem == null) {
            return;
        }

        newsItem.setTitle(newsEditItem.getTitle());
        newsItem.setPreviewText(newsEditItem.getPreviewText());
        newsItem.setUrl(newsEditItem.getUrl());
        newsItem.setNormalImageUrl(newsEditItem.getNormalImageUrl());

        final Disposable disposable = database.update(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processSaving, this::handleError);
        compositeDisposable.add(disposable);
    }

    public void onPublishedDateClicked() {
        final int publishedYear = calendar.get(Calendar.YEAR);
        final int publishedMonth = calendar.get(Calendar.MONTH);
        final int publishedDay = calendar.get(Calendar.DAY_OF_MONTH);

        getViewState().showDatePickerDialog(publishedYear, publishedMonth, publishedDay);
    }

    public void onPublishedTimeClicked() {
        final int publishedHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int publishedMinute = calendar.get(Calendar.MINUTE);

        getViewState().showTimePickerDialog(publishedHour, publishedMinute);
    }

    public void updateDate(int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        updatePublishedDate();
    }

    public void updateTime(int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        updatePublishedDate();
    }

    @Override
    protected void processLoading(@NonNull NewsItem newsItem) {
        super.processLoading(newsItem);

        calendar.setTime(newsItem.getPublishedDate());
        getViewState().setPublishedDateTime(calendar.getTime());
    }

    private void processSaving() {
        getViewState().close();
    }

    private void updatePublishedDate() {
        if (newsItem == null) {
            return;
        }

        newsItem.setPublishedDate(calendar.getTime());
        getViewState().setPublishedDateTime(calendar.getTime());
    }

}
