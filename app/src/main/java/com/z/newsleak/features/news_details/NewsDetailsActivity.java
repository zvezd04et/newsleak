package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NEWS_ID = "EXTRA_NEWS_ID";

    @NonNull
    private TextView titleView;
    @NonNull
    private TextView fullTextView;
    @NonNull
    private ImageView photoView;
    @NonNull
    private TextView publishDateView;

    @Nullable
    private Disposable disposable;

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        final int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);

        disposable = database.getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showNewsDetails);

        titleView = findViewById(R.id.news_details_tv_title);
        fullTextView = findViewById(R.id.news_details_tv_full_text);
        photoView = findViewById(R.id.news_details_iv_photo);
        publishDateView = findViewById(R.id.news_details_tv_publish_date);
    }

    public void showNewsDetails(NewsItem newsItem) {
        setTitle(newsItem.getCategory().getName());

        titleView.setText(newsItem.getTitle());
        fullTextView.setText(newsItem.getPreviewText());
        publishDateView.setText(DateFormatUtils.getRelativeDateTime(this,
                newsItem.getPublishedDate()));

        final RequestManager imageLoader = ImageLoadUtils.getImageLoader(this);
        imageLoader.load(newsItem.getNormalImageUrl()).into(photoView);
    }
}
