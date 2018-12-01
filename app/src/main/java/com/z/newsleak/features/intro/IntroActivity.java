package com.z.newsleak.features.intro;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.z.newsleak.R;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.features.newsfeed.NewsListActivity;
import com.z.newsleak.moxy.MvpAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

public class IntroActivity extends MvpAppCompatActivity implements IntroView {

    @InjectPresenter
    public IntroPresenter presenter;

    @ProvidePresenter
    public IntroPresenter providePresenter() {
        return new IntroPresenter(PreferencesManager.getInstance(this));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void setIntroLayout() {
        setContentView(R.layout.activity_intro);
    }

    @Override
    public void startNextActivity() {
        NewsListActivity.start(this);
        finish();
    }

}
