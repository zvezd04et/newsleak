package com.z.newsleak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.z.newsleak.data.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS = "EXTRA_NEWS";

    public static void start(@NonNull Activity activity, @NonNull NewsItem newsItem) {
        final Intent intent = new Intent(activity, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS, newsItem);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(EXTRA_NEWS);
        final TextView previewText = findViewById(R.id.tv_title);
        previewText.setText(newsItem.getTitle());
    }
}
