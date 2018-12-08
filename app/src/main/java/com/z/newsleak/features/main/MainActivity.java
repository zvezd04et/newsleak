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
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements BaseFragmentListener, NewsListFragmentListener {

    private static final String FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG";
    private static final String FRAGMENT_DETAILS_TAG = "FRAGMENT_DETAILS_TAG";

    private boolean isTwoPanel;
    @NonNull
    private FragmentManager fragmentManager;

    public static void start(@NonNull Context context) {
        Intent startIntent = new Intent(context, MainActivity.class);
        context.startActivity(startIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTwoPanel = findViewById(R.id.frame_detail) != null;
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_list, NewsListFragment.newInstance(), FRAGMENT_LIST_TAG)
                    .addToBackStack(null)
                    .commit();
            return;
        }

        final Fragment detailsFragment = fragmentManager.findFragmentByTag(FRAGMENT_DETAILS_TAG);
        if (detailsFragment == null) {
            return;
        }

        fragmentManager.popBackStackImmediate();
        replaceDetailsFragment(detailsFragment);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void turnBack() {
        fragmentManager.popBackStack();
    }

    @Override
    public void setTitle(@NonNull String title, boolean displayHome) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title);
            ab.setDisplayHomeAsUpEnabled(displayHome & !isTwoPanel);
        }
    }

    @Override
    public void onNewsClicked(int id) {
        final Fragment detailsFragment = fragmentManager.findFragmentByTag(FRAGMENT_DETAILS_TAG);
        if (detailsFragment != null) {
            fragmentManager.popBackStackImmediate();
        }
        replaceDetailsFragment(NewsDetailsFragment.newInstance(id));
    }

    private void replaceDetailsFragment(@NonNull Fragment fragment){
        final int frameId = isTwoPanel ? R.id.frame_detail : R.id.frame_list;
        fragmentManager.beginTransaction()
                .replace(frameId, fragment, FRAGMENT_DETAILS_TAG)
                .addToBackStack(null)
                .commit();
    }
}
