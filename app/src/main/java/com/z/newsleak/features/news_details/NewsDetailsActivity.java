package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

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
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsItem.getFullText());

    }
}
