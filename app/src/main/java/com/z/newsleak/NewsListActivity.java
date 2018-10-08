package com.z.newsleak;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.z.newsleak.data.DataUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity {

    private final NewsListAdapter.OnItemClickListener clickListener = newsItem ->
            NewsDetailsActivity.start(NewsListActivity.this, newsItem);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        final RecyclerView list = findViewById(R.id.news_list_rv);
        list.setAdapter(new NewsListAdapter(this, DataUtils.generateNews(), clickListener));

        final GridLayoutManager gridLayoutManager
                = new GridLayoutManager(this, SupportUtils.getDisplayColumns(this));
        list.setLayoutManager(gridLayoutManager);

        final DividerItemDecoration verticalDivider
                = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        final Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.vertical_divider);
        verticalDivider.setDrawable(dividerDrawable);
        list.addItemDecoration(verticalDivider);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
