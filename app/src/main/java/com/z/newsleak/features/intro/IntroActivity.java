package com.z.newsleak.features.intro;

import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity;
import com.z.newsleak.R;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.features.newsfeed.NewsListActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

public class IntroActivity extends MvpViewStateActivity<IntroContract.View, IntroContract.Presenter, IntroViewState> implements IntroContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @NonNull
    @Override
    public IntroContract.Presenter createPresenter() {
        return new IntroPresenter(PreferencesManager.getInstance(this));
    }

    @NonNull
    @Override
    public IntroViewState createViewState() {
        return new IntroViewState();
    }

    @Override
    public void onNewViewStateInstance() {
        presenter.makeDelay();
    }

    @Override
    public void setIntroLayout() {
        setContentView(R.layout.activity_intro);
        viewState.setIntroVisible(true);
    }

    @Override
    public void startNextActivity() {
        NewsListActivity.start(this);
        finish();
    }

}
