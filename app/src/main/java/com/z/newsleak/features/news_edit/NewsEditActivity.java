package com.z.newsleak.features.news_edit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class NewsEditActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NewsEditActivity";
    private static final String EXTRA_NEWS_ID = "EXTRA_NEWS_ID";

    @NonNull
    private EditText titleEdit;

    @Nullable
    private Disposable disposable;

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int newsId;

    @Nullable
    private NewsItem newsItem;

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsEditActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        titleEdit = findViewById(R.id.edit_et_title);

        newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);
        disposable = database.getNewsById(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showNewsDetails);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                save();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {

        newsItem.setTitle(titleEdit.getText().toString());

        Disposable disposable = database.insert(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessSave, this::handleSaveError);

        compositeDisposable.add(disposable);
    }

    public void showNewsDetails(NewsItem newsItem) {

        this.newsItem = newsItem;
        setTitle(newsItem.getCategory().getName());
        titleEdit.setText(newsItem.getTitle());
    }

    private void onSuccessSave() {
        finish();
    }

    private void handleSaveError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        finish();
    }
}
