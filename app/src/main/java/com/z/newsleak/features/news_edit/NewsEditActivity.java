package com.z.newsleak.features.news_edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.di.modules.NewsItemModule;
import com.z.newsleak.model.NewsEditItem;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.moxy.MvpAppCompatActivity;
import com.z.newsleak.utils.DateFormatUtils;

import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsEditActivity extends MvpAppCompatActivity implements NewsEditView {

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

    @Inject
    @InjectPresenter
    public NewsEditPresenter presenter;

    @ProvidePresenter
    public NewsEditPresenter providePresenter() {
        int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);
        App.getAppComponent().plus(new NewsItemModule(newsId)).inject(this);
        return presenter;
    }

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsEditActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        titleEdit = findViewById(R.id.news_edit_et_title);
        previewEdit = findViewById(R.id.news_edit_et_preview_text);
        urlEdit = findViewById(R.id.news_edit_et_url);
        urlPhotoEdit = findViewById(R.id.news_edit_et_url_photo);
        publishedDateView = findViewById(R.id.news_edit_tv_published_date);
        publishedTimeView = findViewById(R.id.news_edit_tv_published_time);

        publishedDateView.setOnClickListener(v -> presenter.onPublishedDateClicked());
        publishedTimeView.setOnClickListener(v -> presenter.onPublishedTimeClicked());
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_save:
                saveData();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPublishedDateTime(@Nullable Date publishedDate) {
        publishedDateView.setText(DateFormatUtils.getRelativeDate(publishedDate));
        publishedTimeView.setText(DateFormatUtils.getRelativeTime(publishedDate));
    }

    @Override
    public void setData(@NonNull NewsItem newsItem) {
        titleEdit.setText(newsItem.getTitle());
        previewEdit.setText(newsItem.getPreviewText());
        urlEdit.setText(newsItem.getUrl());
        urlPhotoEdit.setText(newsItem.getNormalImageUrl());
    }

    @Override
    public void close() {
        finish();
    }

    private void saveData() {
        final NewsEditItem newsEditItem = new NewsEditItem.Builder()
                .title(titleEdit.getText().toString())
                .previewText(previewEdit.getText().toString())
                .url(urlEdit.getText().toString())
                .normalImageUrl(urlPhotoEdit.getText().toString())
                .build();

        presenter.saveData(newsEditItem);
    }

    @Override
    public void showDatePickerDialog(int publishedYear, int publishedMonth, int publishedDay) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) ->
                        presenter.updateDate(year, monthOfYear, dayOfMonth),
                publishedYear, publishedMonth, publishedDay);
        datePickerDialog.show();
    }

    @Override
    public void showTimePickerDialog(int publishedHour, int publishedMinute) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> presenter.updateTime(hourOfDay, minute),
                publishedHour, publishedMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
}
