package com.z.newsleak.features.news_edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.data.db.NewsDao;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class NewsEditActivity extends AppCompatActivity {

    private static final String LOG_TAG = "NewsEditActivity";
    private static final String EXTRA_NEWS_ID = "EXTRA_NEWS_ID";

    @NonNull
    private EditText titleEdit;
    @NonNull
    private EditText previewEdit;
    @NonNull
    private EditText urlEdit;
    @NonNull
    private EditText urlPhotoEdit;
    @NonNull
    private TextView publishedDateView;
    @NonNull
    private TextView publishedTimeView;

    @Nullable
    private Disposable disposable;

    @NonNull
    private NewsDao database = App.getDatabase().getNewsDao();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private Calendar calendar = Calendar.getInstance();

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

        titleEdit = findViewById(R.id.news_edit_et_title);
        previewEdit = findViewById(R.id.news_edit_et_preview_text);
        urlEdit = findViewById(R.id.news_edit_et_url);
        urlPhotoEdit = findViewById(R.id.news_edit_et_url_photo);
        publishedDateView = findViewById(R.id.news_edit_tv_published_date);
        publishedTimeView = findViewById(R.id.news_edit_tv_published_time);

        publishedDateView.setOnClickListener(v -> showDatePickerDialog());
        publishedTimeView.setOnClickListener(v -> showTimePickerDialog());

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

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_save:
                save();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDatePickerDialog() {
        final int publishedYear = calendar.get(Calendar.YEAR);
        final int publishedMonth = calendar.get(Calendar.MONTH);
        final int publishedDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    publishedDateView.setText(DateFormatUtils.getRelativeDate(calendar.getTime()));
                }, publishedYear, publishedMonth, publishedDay);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final int publishedHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int publishedMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    publishedTimeView.setText(DateFormatUtils.getRelativeTime(calendar.getTime()));
                }, publishedHour, publishedMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
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

        calendar.setTime(newsItem.getPublishedDate());

        titleEdit.setText(newsItem.getTitle());
        previewEdit.setText(newsItem.getPreviewText());
        urlEdit.setText(newsItem.getUrl());
        urlPhotoEdit.setText(newsItem.getNormalImageUrl());
        publishedDateView.setText(DateFormatUtils.getRelativeDate(newsItem.getPublishedDate()));
        publishedTimeView.setText(DateFormatUtils.getRelativeTime(newsItem.getPublishedDate()));
    }

    private void onSuccessSave() {
        finish();
    }

    private void handleSaveError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        finish();
    }
}
