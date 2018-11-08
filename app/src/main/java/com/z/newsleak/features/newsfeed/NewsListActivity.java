package com.z.newsleak.features.newsfeed;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.z.newsleak.data.Category;
import com.z.newsleak.data.LoadState;
import com.z.newsleak.features.news_details.NewsDetailsActivity;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.network.NewsResponse;
import com.z.newsleak.network.api.RestApi;
import com.z.newsleak.network.dto.NewsItemDTO;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.ui.LoadingScreenHolder;
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

public class NewsListActivity extends MvpActivity<NewsListView, NewsListPresenter>
        implements NewsListView {

    private static final String LOG_TAG = "NewsListActivity";
    private static final String BUNDLE_LIST_KEY = "BUNDLE_LIST_KEY";
    private static final String NEWS_ITEMS_KEY = "NEWS_ITEMS_KEY";

    @NonNull
    private RecyclerView rvNewsfeed;
    @Nullable
    private NewsListAdapter newsAdapter;

    @NonNull
    private LoadingScreenHolder loadingScreen;

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @NonNull
    private Category currentCategory = Category.HOME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        rvNewsfeed = findViewById(R.id.news_list_rv);
        setupRecyclerView(rvNewsfeed);
        loadingScreen = new LoadingScreenHolder(rvNewsfeed, btn -> presenter.loadNews(currentCategory));

        final Spinner spinner = findViewById(R.id.news_list_sp_section);
        setupSpinner(spinner);

        if (savedInstanceState == null || !savedInstanceState.containsKey(NEWS_ITEMS_KEY)) {
            presenter.loadNews(currentCategory);
        } else {
            Parcelable listState = savedInstanceState.getParcelable(BUNDLE_LIST_KEY);
            rvNewsfeed.getLayoutManager().onRestoreInstanceState(listState);
            List<NewsItem> newsItems = (List<NewsItem>) savedInstanceState.getSerializable(NEWS_ITEMS_KEY);
            if (newsAdapter != null && newsItems != null) newsAdapter.replaceItems(newsItems);
            loadingScreen.showState(LoadState.HAS_DATA);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        SupportUtils.disposeSafely(compositeDisposable);
        loadingScreen.showState(LoadState.HAS_DATA);
    }

    @NonNull
    @Override
    public NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BUNDLE_LIST_KEY, rvNewsfeed.getLayoutManager().onSaveInstanceState());
        outState.putSerializable(NEWS_ITEMS_KEY, (Serializable) newsAdapter.getNewsItems());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public void showLoading(){
        loadingScreen.showState(LoadState.LOADING);
    }

    @Override
    public void processResponse(@NonNull Response<NewsResponse> response) {

        if (!response.isSuccessful()) {
            loadingScreen.showState(LoadState.SERVER_ERROR);
            return;
        }

        final NewsResponse body = response.body();
        if (body == null) {
            loadingScreen.showState(LoadState.HAS_NO_DATA);
            return;
        }

        final List<NewsItemDTO> newsItemDTOs = body.getResults();
        if (newsItemDTOs == null || newsItemDTOs.isEmpty()) {
            loadingScreen.showState(LoadState.HAS_NO_DATA);
            return;
        }

        List<NewsItem> news = NewsItemConverter.convertFromDtos(newsItemDTOs, currentCategory);
        if (newsAdapter != null && news != null) newsAdapter.replaceItems(news);
        loadingScreen.showState(LoadState.HAS_DATA);
    }

    @Override
    public void handleError(@NonNull Throwable th) {
        Log.e(LOG_TAG, th.getMessage(), th);
        loadingScreen.showState(LoadState.SERVER_ERROR);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        newsAdapter = new NewsListAdapter(this, newsItem -> NewsDetailsActivity.start(this, newsItem));
        recyclerView.setAdapter(newsAdapter);

        final int columnsCount = SupportUtils.getNewsColumnsCount(this);
        if (columnsCount == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, columnsCount));
        }

        final DividerItemDecoration verticalDivider
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        final Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        if (dividerDrawable != null) {
            verticalDivider.setDrawable(dividerDrawable);
        }
        recyclerView.addItemDecoration(verticalDivider);
    }

    private void setupSpinner(@NonNull Spinner spinner) {
        final ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, R.layout.section_spinner_item, Category.values());
        adapter.setDropDownViewResource(R.layout.section_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Category category = Category.values()[position];
                if (!category.equals(currentCategory)) {
                    presenter.loadNews(category);
                    currentCategory = category;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
