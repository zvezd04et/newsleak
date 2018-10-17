package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
        setTitle(newsItem.getCategory().getName());

        final TextView titleView = findViewById(R.id.news_details_tv_title);
        titleView.setText(newsItem.getTitle());
        final TextView fullTextView = findViewById(R.id.news_details_tv_full_text);
        fullTextView.setText(newsItem.getFullText());

        final RequestManager imageLoader = ImageLoadUtils.getImageLoader(this);
        final ImageView photoView = findViewById(R.id.news_details_iv_photo);
        imageLoader.load(newsItem.getImageUrl()).into(photoView);

        final TextView publishDateView = findViewById(R.id.news_details_tv_publish_date);
        publishDateView.setText(DateFormatUtils.getRelativeDateTime(this,
                newsItem.getPublishDate()));

    }
}
