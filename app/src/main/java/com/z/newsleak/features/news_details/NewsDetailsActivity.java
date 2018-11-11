package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadingScreenHolder;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_SECTION = "EXTRA_NEWS_TITLE";
    private static final String EXTRA_NEWS_URL = "EXTRA_NEWS_URL";

    public static void start(@NonNull Context context, @NonNull NewsItem newsItem) {
        final Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_SECTION, newsItem.getSection());
        intent.putExtra(EXTRA_NEWS_URL, newsItem.getArticleUrl());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final String section = getIntent().getStringExtra(EXTRA_NEWS_SECTION);
        setTitle(section);

        final WebView webView = findViewById(R.id.news_details_wv_full_text);
        final LoadingScreenHolder loadingScreen = new LoadingScreenHolder(webView, btn -> webView.reload());
        webView.setWebViewClient(new NewsWebViewClient(loadingScreen));
        final String url = getIntent().getStringExtra(EXTRA_NEWS_URL);
        webView.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }
}
