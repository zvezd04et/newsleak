package com.z.newsleak.features.newsfeed;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.z.newsleak.features.news_details.NewsDetailsActivity;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DataUtils;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.utils.ErrorHandler;
import com.z.newsleak.utils.SupportUtils;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class NewsListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NewsListActivity";
    private static final String BUNDLE_LIST_KEY = "BUNDLE_LIST_KEY";
    private static final String NEWS_ITEMS_KEY = "NEWS_ITEMS_KEY";

    @Nullable
    private RecyclerView list;
    @Nullable
    private ProgressBar progressBar;
    @Nullable
    private NewsListAdapter newsAdapter;
    @Nullable
    private View errorContent;

    @Nullable
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RxJavaPlugins.setErrorHandler(ErrorHandler.get());

        progressBar = findViewById(R.id.news_list_progress);
        errorContent = findViewById(R.id.error_content);

        final Button retryBtn = findViewById(R.id.error_btn_retry);
        retryBtn.setOnClickListener(btn -> loadNews());

        list = findViewById(R.id.news_list_rv);
        newsAdapter = new NewsListAdapter(this, newsItem -> NewsDetailsActivity.start(this, newsItem));
        if (list != null) {
            list.setAdapter(newsAdapter);
        }

        final int columnsCount = SupportUtils.getNewsColumnsCount(this);
        if (columnsCount == 1) {
            list.setLayoutManager(new LinearLayoutManager(this));
        } else {
            list.setLayoutManager(new GridLayoutManager(this, columnsCount));
        }

        final DividerItemDecoration verticalDivider
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        final Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        if (dividerDrawable != null) {
            verticalDivider.setDrawable(dividerDrawable);
        }
        list.addItemDecoration(verticalDivider);

        if (savedInstanceState == null || !savedInstanceState.containsKey(NEWS_ITEMS_KEY)) {
            loadNews();
        } else {
            Parcelable listState = savedInstanceState.getParcelable(BUNDLE_LIST_KEY);
            list.getLayoutManager().onRestoreInstanceState(listState);
            List<NewsItem> newsItems = (List<NewsItem>) savedInstanceState.getSerializable(NEWS_ITEMS_KEY);
            updateNews(newsItems);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        showProgress(false);

        SupportUtils.disposeSafely(disposable);
        disposable = null;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (list != null) {
            outState.putParcelable(BUNDLE_LIST_KEY, list.getLayoutManager().onSaveInstanceState());
            outState.putSerializable(NEWS_ITEMS_KEY, (Serializable) newsAdapter.getNewsItems());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar = null;
        list = null;
        newsAdapter = null;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutActivity.start(this);
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadNews() {
        showProgress(true);
        disposable = Observable.fromCallable(DataUtils::generateNews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateNews,
                        this::handleError);
    }

    private void updateNews(@Nullable List<NewsItem> news) {
        if (newsAdapter != null && news != null) newsAdapter.replaceItems(news);

        SupportUtils.setVisible(list, true);
        SupportUtils.setVisible(progressBar, false);
        SupportUtils.setVisible(errorContent, false);
    }

    private void showProgress(boolean shouldShow) {
        SupportUtils.setVisible(list, !shouldShow);
        SupportUtils.setVisible(progressBar, shouldShow);
        SupportUtils.setVisible(errorContent, false);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);

        SupportUtils.setVisible(list, false);
        SupportUtils.setVisible(progressBar, false);
        SupportUtils.setVisible(errorContent, true);
    }


}
