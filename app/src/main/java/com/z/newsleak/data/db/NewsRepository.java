package com.z.newsleak.data.db;

import com.z.newsleak.App;
import com.z.newsleak.model.NewsItem;

import java.util.List;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsRepository {

    @NonNull
    private static final NewsDao newsDao = App.getDatabase().getNewsDao();
    @Nullable
    private static NewsRepository repository;

    @NonNull
    public static NewsRepository getInstance() {
        if (repository == null) {
            synchronized (NewsRepository.class) {
                if (repository == null) {
                    repository = new NewsRepository();
                }
            }
        }
        return repository;
    }

    public Completable saveData(final List<NewsItem> newsList) {
        return Completable.fromCallable((Callable<Void>) () -> {
            newsDao.deleteAll();
            newsDao.insertAll(newsList);

            return null;
        });
    }

    public Completable saveItem(final NewsItem newsItem) {
        return newsDao.update(newsItem);
    }

    public Observable<List<NewsItem>> getDataObservable() {
        return newsDao.getAll();
    }

    public Observable<NewsItem> getItemObservable(int id) {
        return newsDao.getNewsById(id);
    }

    public Completable deleteItem(int id) {
        return Completable.fromCallable((Callable<Void>) () -> {

            newsDao.deleteById(id);
            return null;
        });
    }
}
