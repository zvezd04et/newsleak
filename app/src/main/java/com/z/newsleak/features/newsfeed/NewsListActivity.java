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

import com.z.newsleak.data.LoadState;
import com.z.newsleak.features.news_details.NewsDetailsActivity;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.network.NewsResponse;
import com.z.newsleak.network.api.RestApi;
import com.z.newsleak.network.dto.NewsItemDTO;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.utils.NewsItemConverter;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

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

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

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
            if (newsAdapter != null && newsItems != null) newsAdapter.replaceItems(newsItems);
            showState(LoadState.HAS_DATA);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        SupportUtils.disposeSafely(disposable);
        disposable = null;
        showState(LoadState.HAS_DATA);
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
        final Disposable searchDisposable = RestApi.getInstance()
                .getApi()
                .getNews("home")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> showState(LoadState.LOADING))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResponse, this::handleError);
        compositeDisposable.add(searchDisposable);
    }

    private void processResponse(@NonNull Response<NewsResponse> response) {

        if (!response.isSuccessful()) {
            showState(LoadState.SERVER_ERROR);
            return;
        }

        final NewsResponse body = response.body();
        if (body == null) {
            showState(LoadState.HAS_NO_DATA);
            return;
        }

        final List<NewsItemDTO> newsItemDTOs = body.getResults();
        if (newsItemDTOs == null || newsItemDTOs.isEmpty()) {
            showState(LoadState.HAS_NO_DATA);
            return;
        }

        List<NewsItem> news = NewsItemConverter.convertFromDtos(newsItemDTOs);
        if (newsAdapter != null && news != null) newsAdapter.replaceItems(news);
        showState(LoadState.HAS_DATA);
    }

    private void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        showState(LoadState.SERVER_ERROR);
    }

    public void showState(@NonNull LoadState state) {

        switch (state) {
            case HAS_DATA:
                SupportUtils.setVisible(list, true);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, false);
                break;
            case HAS_NO_DATA:
                SupportUtils.setVisible(list, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                break;
            case NETWORK_ERROR:
                SupportUtils.setVisible(list, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                break;
            case SERVER_ERROR:
                SupportUtils.setVisible(list, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                break;
            case LOADING:
                SupportUtils.setVisible(list, false);
                SupportUtils.setVisible(progressBar, true);
                SupportUtils.setVisible(errorContent, false);
                break;

            default:
                Log.d(LOG_TAG, "Unknown state: " + state);
        }
    }

}
