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

public class NewsListActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NewsListActivity";
    private static final String BUNDLE_LIST_KEY = "BUNDLE_LIST_KEY";
    private static final String NEWS_ITEMS_KEY = "NEWS_ITEMS_KEY";

    @NonNull
    private RecyclerView rvNewsfeed;
    @Nullable
    private NewsListAdapter newsAdapter;
    @Nullable
    private Disposable disposable;

    @NonNull
    private LoadingScreenHolder loadingScreen;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String currentSection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        rvNewsfeed = findViewById(R.id.news_list_rv);
        setupRecyclerView(rvNewsfeed);
        loadingScreen = new LoadingScreenHolder(rvNewsfeed, btn -> loadNews(currentSection));

        final Spinner spinner = findViewById(R.id.news_list_sp_section);
        setupSpinner(spinner);

        if (savedInstanceState == null || !savedInstanceState.containsKey(NEWS_ITEMS_KEY)) {
            loadNews(currentSection);
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
        SupportUtils.disposeSafely(disposable);
        disposable = null;
        loadingScreen.showState(LoadState.HAS_DATA);
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

    private void loadNews(String section) {
        final Disposable searchDisposable = RestApi.getInstance()
                .getApi()
                .getNews(section)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> loadingScreen.showState(LoadState.LOADING))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::processResponse, this::handleError);
        compositeDisposable.add(searchDisposable);
    }

    private void processResponse(@NonNull Response<NewsResponse> response) {

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

        List<NewsItem> news = NewsItemConverter.convertFromDtos(newsItemDTOs);
        if (newsAdapter != null && news != null) newsAdapter.replaceItems(news);
        loadingScreen.showState(LoadState.HAS_DATA);
    }

    private void handleError(@NonNull Throwable th) {
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
                final String section = Category.values()[position].getSection();
                if (!section.equals(currentSection)) {
                    loadNews(section);
                    currentSection = section;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
