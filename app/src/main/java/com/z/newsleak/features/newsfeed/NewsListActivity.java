package com.z.newsleak.features.newsfeed;

import android.content.Context;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
import com.z.newsleak.model.Category;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.features.news_details.NewsDetailsActivity;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.ui.LoadingScreenHolder;
import com.z.newsleak.utils.SupportUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends MvpViewStateActivity<NewsListContract.View, NewsListContract.Presenter, NewsListViewState> implements NewsListContract.View {

    private static final String LOG_TAG = "NewsListActivity";
    private static final String BUNDLE_LIST_KEY = "BUNDLE_LIST_KEY";

    @NonNull
    private RecyclerView rvNewsfeed;
    @NonNull
    private Spinner spinner;
    @NonNull
    private LoadingScreenHolder loadingScreen;

    @Nullable
    private Parcelable rvState;
    @Nullable
    private NewsListAdapter newsAdapter;


    public static void start(@NonNull Context context) {
        final Intent intent = new Intent(context, NewsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        rvNewsfeed = findViewById(R.id.news_list_rv);
        setupRecyclerView(rvNewsfeed);

        spinner = findViewById(R.id.news_list_sp_section);
        setupSpinner(spinner);

        final View.OnClickListener clickListener = btn ->presenter.loadNews((Category) spinner.getSelectedItem());

        final FloatingActionButton fab = findViewById(R.id.news_list_fab_refresh);
        fab.setOnClickListener(clickListener);

        loadingScreen = new LoadingScreenHolder(rvNewsfeed, clickListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loadingScreen.showState(LoadState.HAS_DATA);
    }

    @NonNull
    @Override
    public NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }

    @NonNull
    @Override
    public NewsListViewState createViewState() {
        return new NewsListViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        final RecyclerView.LayoutManager layoutManager = rvNewsfeed.getLayoutManager();
        if (layoutManager != null) {
            outState.putParcelable(BUNDLE_LIST_KEY, layoutManager.onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            rvState = savedInstanceState.getParcelable(BUNDLE_LIST_KEY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final RecyclerView.LayoutManager layoutManager = rvNewsfeed.getLayoutManager();
        if (rvState != null && layoutManager != null) {
            layoutManager.onRestoreInstanceState(rvState);
        }
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
    public void showNews(List<NewsItem> news) {
        if (newsAdapter == null) {
            return;
        }
        newsAdapter.replaceItems(news);
        loadingScreen.showState(LoadState.HAS_DATA);
        viewState.setNews(news);
    }

    @Override
    public void showState(@NonNull LoadState state) {
        loadingScreen.showState(state);
        viewState.setState(state);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        newsAdapter = new NewsListAdapter(this, newsItem -> NewsDetailsActivity.start(this, newsItem.getId()));
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
                presenter.onSpinnerCategorySelected(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
