package com.z.newsleak.features.news_details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.z.newsleak.App;
import com.z.newsleak.features.news_edit.NewsEditActivity;
import com.z.newsleak.R;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

import java.util.concurrent.Callable;

public class NewsDetailsActivity extends MvpActivity<NewsDetailsContract.View, NewsDetailsContract.Presenter> implements NewsDetailsContract.View {

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
    private int newsId;

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);
        presenter.getData(newsId);

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
                presenter.deleteData();
                return true;

            case R.id.action_edit:
                NewsEditActivity.start(this, newsId);
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public NewsDetailsContract.Presenter createPresenter() {
        return new NewsDetailsPresenter();
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
}
