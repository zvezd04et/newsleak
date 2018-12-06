package com.z.newsleak.features.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.z.newsleak.R;
import com.z.newsleak.features.base.BaseFragmentListener;
import com.z.newsleak.features.news_details.NewsDetailsFragment;
import com.z.newsleak.features.newsfeed.NewsListFragment;
import com.z.newsleak.features.newsfeed.NewsListFragmentListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity
        implements BaseFragmentListener, NewsListFragmentListener {

    private static final String FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG";
    private static final String FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG";

    public static void start(@NonNull Context context) {
        Intent startIntent = new Intent(context, MainActivity.class);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_list, NewsListFragment.newInstance(), FRAGMENT_LIST_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void turnBack() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onNewsClicked(int id) {

        Fragment detailsFragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DETAILS_TAG);
        if (detailsFragment != null) {
            getSupportFragmentManager().popBackStackImmediate();
        }

        int detailsFrameId = R.id.frame_list;
        getSupportFragmentManager().beginTransaction()
                .replace(detailsFrameId, NewsDetailsFragment.newInstance(id), FRAGMENT_DETAILS_TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setTitle(@NonNull String title, boolean displayHome) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
            ab.setDisplayHomeAsUpEnabled(displayHome);
        }
    }

}
