package com.z.newsleak.features.news_details;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.bumptech.glide.RequestManager;
import com.z.newsleak.App;
import com.z.newsleak.R;
import com.z.newsleak.di.modules.NewsItemModule;
import com.z.newsleak.features.base.BaseFragment;
import com.z.newsleak.features.news_edit.NewsEditActivity;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsDetailsFragment extends BaseFragment implements NewsDetailsView {

    private static final String LOG_TAG = "NewsDetailsFragment";
    private static final String EXTRA_NEWS_ID = "EXTRA_NEWS_ID";

    @NonNull
    private TextView titleView;
    @NonNull
    private TextView fullTextView;
    @NonNull
    private ImageView photoView;
    @NonNull
    private TextView publishDateView;

    @Inject
    @InjectPresenter
    public NewsDetailsPresenter presenter;

    @ProvidePresenter
    public NewsDetailsPresenter providePresenter() {
        final int newsId = getArguments().getInt(EXTRA_NEWS_ID, 0);
        App.getNewsUpdateComponent().plus(new NewsItemModule(newsId)).inject(this);
        return presenter;
    }

    public static NewsDetailsFragment newInstance(int id) {
        NewsDetailsFragment newsDetailsFragment = new NewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_NEWS_ID, id);
        newsDetailsFragment.setArguments(bundle);

        return newsDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);

        titleView = view.findViewById(R.id.news_details_tv_title);
        fullTextView = view.findViewById(R.id.news_details_tv_full_text);
        photoView = view.findViewById(R.id.news_details_iv_photo);
        publishDateView = view.findViewById(R.id.news_details_tv_publish_date);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                turnBack();
                return true;

            case R.id.action_delete:
                presenter.onDeleteSelected();
                return true;

            case R.id.action_edit:
                presenter.onEditSelected();
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setData(@NonNull NewsItem newsItem) {
        setTitle(newsItem.getCategory().getName(), true);

        titleView.setText(newsItem.getTitle());
        fullTextView.setText(newsItem.getPreviewText());
        publishDateView.setText(DateFormatUtils.getRelativeDateTime(getContext(),
                newsItem.getPublishedDate()));

        final RequestManager imageLoader = ImageLoadUtils.getImageLoader(getContext());
        imageLoader.load(newsItem.getLargeImageUrl()).into(photoView);
    }

    @Override
    public void close() {
        turnBack();
    }

    @Override
    public void openEditorActivity(int newsId) {
        NewsEditActivity.start(getContext(), newsId);
    }

}
