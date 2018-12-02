package com.z.newsleak.features.intro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.z.newsleak.R;
import com.z.newsleak.data.PreferencesManager;
import com.z.newsleak.features.newsfeed.NewsListActivity;
import com.z.newsleak.moxy.MvpAppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import me.relex.circleindicator.CircleIndicator;

public class IntroActivity extends MvpAppCompatActivity implements IntroView {

    private static final int[] drawables = new int[]{
            R.drawable.intro_screen_newsfeed,
            R.drawable.intro_screen_details,
            R.drawable.intro_screen_select_section};

    @NonNull
    private Button skipBtn;
    @NonNull
    private Button nextBtn;

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

        final ViewPager viewPager = findViewById(R.id.intro_vp_preview);
        setupViewPager(viewPager);

        skipBtn = findViewById(R.id.intro_btn_skip);
        skipBtn.setOnClickListener(v -> startNextActivity());

        nextBtn = findViewById(R.id.intro_btn_next);
        nextBtn.setOnClickListener(v -> {
            int nextItem = viewPager.getCurrentItem() + 1;
            if (nextItem < drawables.length) {
                viewPager.setCurrentItem(nextItem);
            } else {
                startNextActivity();
            }
        });
    }

    @Override
    public void startNextActivity() {
        NewsListActivity.start(this);
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        final IntroPagerAdapter adapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        final CircleIndicator circleIndicator = findViewById(R.id.intro_indicator);
        circleIndicator.setViewPager(viewPager);

        final OnPageChangeListener viewPagerPageChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == drawables.length - 1) {
                    nextBtn.setText(R.string.intro_action_done);
                    skipBtn.setVisibility(View.INVISIBLE);
                } else {
                    nextBtn.setText(R.string.intro_action_next);
                    skipBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                // Code goes here
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        };
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    private static class IntroPagerAdapter extends FragmentPagerAdapter {

        IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragment.newInstance(drawables[position]);
        }

        @Override
        public int getCount() {
            return drawables.length;
        }
    }

}
