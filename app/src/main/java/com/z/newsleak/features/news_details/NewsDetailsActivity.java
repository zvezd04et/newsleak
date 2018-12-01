package com.z.newsleak.features.news_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.RequestManager;
import com.z.newsleak.R;
import com.z.newsleak.features.news_edit.NewsEditActivity;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.moxy.MvpAppCompatActivity;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsDetailsActivity extends MvpAppCompatActivity implements NewsDetailsView {

    private static final String LOG_TAG = "NewsDetailsActivity";
    private static final String EXTRA_NEWS_ID = "EXTRA_NEWS_ID";

    @NonNull
    private TextView titleView;
    @NonNull
    private TextView fullTextView;
    @NonNull
    private ImageView photoView;
    @NonNull
    private TextView publishDateView;

    @InjectPresenter
    public NewsDetailsPresenter presenter;

    @ProvidePresenter
    public NewsDetailsPresenter providePresenter() {
        final int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);
        return new NewsDetailsPresenter(newsId);
    }

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        titleView = findViewById(R.id.news_details_tv_title);
        fullTextView = findViewById(R.id.news_details_tv_full_text);
        photoView = findViewById(R.id.news_details_iv_photo);
        publishDateView = findViewById(R.id.news_details_tv_publish_date);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_delete:
                presenter.onDeleteSelected();
                return true;

            case R.id.action_edit:
                presenter.onEditSelected();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setData(@NonNull NewsItem newsItem) {
        setTitle(newsItem.getCategory().getName());

        titleView.setText(newsItem.getTitle());
        fullTextView.setText(newsItem.getPreviewText());
        publishDateView.setText(DateFormatUtils.getRelativeDateTime(this,
                newsItem.getPublishedDate()));

        final RequestManager imageLoader = ImageLoadUtils.getImageLoader(this);
        imageLoader.load(newsItem.getLargeImageUrl()).into(photoView);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void openEditorActivity(int newsId) {
        NewsEditActivity.start(this, newsId);
    }

}
