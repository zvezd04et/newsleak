package com.z.newsleak.features.intro;

import android.os.Bundle;

import com.z.newsleak.R;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.features.newsfeed.NewsListActivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IntroActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final long TIME_DELAY = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Disposable disposable = Completable.complete()
                .delay(TIME_DELAY, TimeUnit.SECONDS)
                .subscribe(this::startSecondActivity);
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    private void startSecondActivity() {
        NewsListActivity.start(this);
        finish();
    }

}
