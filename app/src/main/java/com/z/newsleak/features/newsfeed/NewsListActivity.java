package com.z.newsleak.features.newsfeed;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.z.newsleak.features.news_details.NewsDetailsActivity;
import com.z.newsleak.R;
import com.z.newsleak.utils.DataUtils;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.utils.DataLoadUtils;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity implements NewsListView {

    private static final String LOG_TAG = "NewsListActivity";

    @Nullable
    private Thread thread;
    @Nullable
    private ProgressBar progressBar;
    private RecyclerView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        progressBar = findViewById(R.id.news_list_progress);
        list = findViewById(R.id.news_list_rv);

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

    }

    @Override
    protected void onStart() {
        super.onStart();

        thread = new Thread(new DataLoadUtils(new Handler(), this));
        thread.start();

        showProgress(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (thread != null) {
            thread.interrupt();
        }
        thread = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar = null;
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
    public void showProgress(boolean shouldShow) {
        SupportUtils.setVisible(progressBar, shouldShow);
    }

    @Override
    public void updateNews() {
        final NewsListAdapter newsAdapter = new NewsListAdapter(this, newsItem -> NewsDetailsActivity.start(this, newsItem));
        newsAdapter.replaceItems(DataUtils.generateNews());
        list.setAdapter(newsAdapter);
    }
}
