package com.z.newsleak.features.news_edit;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
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

public class NewsEditActivity extends MvpViewStateActivity<NewsEditContract.View, NewsEditContract.Presenter, NewsEditViewState> implements NewsEditContract.View {

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
    @NonNull
    private Calendar calendar = Calendar.getInstance();

    public static void start(@NonNull Context context, int id) {
        final Intent intent = new Intent(context, NewsEditActivity.class);
        intent.putExtra(EXTRA_NEWS_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        final int newsId = getIntent().getIntExtra(EXTRA_NEWS_ID, 0);
        presenter.getData(newsId);

        titleEdit = findViewById(R.id.news_edit_et_title);
        previewEdit = findViewById(R.id.news_edit_et_preview_text);
        urlEdit = findViewById(R.id.news_edit_et_url);
        urlPhotoEdit = findViewById(R.id.news_edit_et_url_photo);
        publishedDateView = findViewById(R.id.news_edit_tv_published_date);
        publishedTimeView = findViewById(R.id.news_edit_tv_published_time);

        publishedDateView.setOnClickListener(v -> showDatePickerDialog());
        publishedTimeView.setOnClickListener(v -> showTimePickerDialog());
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
                presenter.saveData();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public NewsEditPresenter createPresenter() {
        return new NewsEditPresenter();
    }

    @NonNull
    @Override
    public NewsEditViewState createViewState() {
        return new NewsEditViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        //specify what to do on first Activity start
    }

    @Override
    public void setCalendar(@NonNull Calendar calendar) {
        this.calendar = calendar;
        publishedDateView.setText(DateFormatUtils.getRelativeDate(calendar.getTime()));
        publishedTimeView.setText(DateFormatUtils.getRelativeTime(calendar.getTime()));
    }

    @Override
    public void setData(@NonNull NewsItem newsItem) {
        calendar.setTime(newsItem.getPublishedDate());
        titleEdit.setText(newsItem.getTitle());
        previewEdit.setText(newsItem.getPreviewText());
        urlEdit.setText(newsItem.getUrl());
        urlPhotoEdit.setText(newsItem.getNormalImageUrl());
        publishedDateView.setText(DateFormatUtils.getRelativeDate(newsItem.getPublishedDate()));
        publishedTimeView.setText(DateFormatUtils.getRelativeTime(newsItem.getPublishedDate()));
        viewState.setCalendar(calendar);
    }

    @Override
    public void updateData(@NonNull NewsItem newsItem) {
        newsItem.setTitle(titleEdit.getText().toString());
        newsItem.setPreviewText(previewEdit.getText().toString());
        newsItem.setUrl(urlEdit.getText().toString());
        newsItem.setNormalImageUrl(urlPhotoEdit.getText().toString());
        newsItem.setPublishedDate(calendar.getTime());
    }

    @Override
    public void close() {
        finish();
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
                    viewState.setCalendar(calendar);
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
                    viewState.setCalendar(calendar);
                }, publishedHour, publishedMinute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

}
