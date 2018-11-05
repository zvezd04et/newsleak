package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadingScreenHolder;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS = "EXTRA_NEWS";

    public static void start(@NonNull Context context, @NonNull NewsItem newsItem) {
        final Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS, newsItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS);
        setTitle(newsItem.getSection());

        final WebView webView = findViewById(R.id.news_details_wv_full_text);
        final LoadingScreenHolder loadingScreen = new LoadingScreenHolder(webView, btn -> webView.reload());
        webView.setWebViewClient(new NewsWebViewClient(loadingScreen));
        webView.loadUrl(newsItem.getArticleUrl());
    }
}
