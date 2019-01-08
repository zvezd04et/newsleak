package com.z.newsleak.features.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.z.newsleak.R;
import com.z.newsleak.di.DI;
import com.z.newsleak.features.about_info.AboutActivity;
import com.z.newsleak.features.base.BaseFragment;
import com.z.newsleak.model.Category;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.ui.LoadState;
import com.z.newsleak.ui.LoadingScreenHolder;
import com.z.newsleak.utils.ViewUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListFragment extends BaseFragment implements NewsListView {

    private static final String LOG_TAG = "NewsListFragment";

    @NonNull
    private RecyclerView rvNewsfeed;
    @NonNull
    private Spinner spinner;
    @NonNull
    private LoadingScreenHolder loadingScreen;
    @Nullable
    private NewsListAdapter newsAdapter;
    @Nullable
    private NewsListFragmentListener listener;

    @Inject
    @InjectPresenter
    public NewsListPresenter presenter;

    @ProvidePresenter
    public NewsListPresenter providePresenter() {
        DI.getAppComponent().inject(this);
        return presenter;
    }

    public static void start(@NonNull Context context) {
        final Intent intent = new Intent(context, NewsListFragment.class);
        context.startActivity(intent);
    }

    @NonNull
    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NewsListFragmentListener) {
            listener = (NewsListFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        rvNewsfeed = view.findViewById(R.id.news_list_rv);
        setupRecyclerView();

        spinner = view.findViewById(R.id.news_list_sp_section);
        setupSpinner();

        final View.OnClickListener clickListener = btn -> presenter.loadNews((Category) spinner.getSelectedItem());

        final FloatingActionButton fab = view.findViewById(R.id.news_list_fab_refresh);
        fab.setOnClickListener(clickListener);

        loadingScreen = new LoadingScreenHolder(rvNewsfeed, clickListener);

        setTitle(getString(R.string.app_name), false);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                AboutActivity.start(getContext());
                return true;

            default:
                Log.d(LOG_TAG, "Selected unknown menu item");
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showNews(@NonNull List<NewsItem> news) {
        if (newsAdapter == null) {
            return;
        }
        newsAdapter.replaceItems(news);
        loadingScreen.showState(LoadState.HAS_DATA);
        ViewUtils.setVisible(rvNewsfeed, true);
    }

    @Override
    public void showState(@NonNull LoadState state) {
        loadingScreen.showState(state);
    }

    @Override
    public void setSpinnerSelection(int position) {
        spinner.setSelection(position);
    }


    public void setupSpinner() {
        final ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), R.layout.section_spinner_item, Category.values());
        adapter.setDropDownViewResource(R.layout.section_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);
        presenter.onSetupSpinnerSelection();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Category category = Category.values()[position];
                presenter.onSpinnerCategorySelected(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupRecyclerView() {
        newsAdapter = new NewsListAdapter(getContext(), newsItem -> listener.onNewsClicked(newsItem.getId()));
        rvNewsfeed.setAdapter(newsAdapter);

        final int columnsCount = getResources().getInteger(R.integer.news_columns_count);
        rvNewsfeed.setLayoutManager(new GridLayoutManager(getContext(), columnsCount));

        final DividerItemDecoration verticalDivider
                = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        final Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.vertical_divider);
        if (dividerDrawable != null) {
            verticalDivider.setDrawable(dividerDrawable);
        }
        rvNewsfeed.addItemDecoration(verticalDivider);
    }
}
