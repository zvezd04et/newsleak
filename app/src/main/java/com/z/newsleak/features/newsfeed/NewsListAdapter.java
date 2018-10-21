package com.z.newsleak.features.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.z.newsleak.R;
import com.z.newsleak.model.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> newsItems = new ArrayList<>();
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;


    public NewsListAdapter(@NonNull Context context, @Nullable OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.inflater = LayoutInflater.from(context);
        this.imageLoader = ImageLoadUtils.getImageLoader(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public void replaceItems(@NonNull List<NewsItem> newItems) {
        newsItems.clear();
        newsItems.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    public List<NewsItem> getNewsItems() {
        return newsItems;
    }

    @Override
    public int getItemViewType(int position) {

        switch (newsItems.get(position).getCategory()) {
            case ANIMALS:
                return R.layout.animal_item_news;
            default:
                return R.layout.default_item_news;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @NonNull
        private final ImageView photoView;
        @NonNull
        private final TextView previewView;
        @NonNull
        private final TextView categoryView;
        @NonNull
        private final TextView titleView;
        @NonNull
        private final TextView publishDateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (clickListener != null && position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(newsItems.get(position));
                }
            });

            photoView = itemView.findViewById(R.id.item_news_iv_photo);
            previewView = itemView.findViewById(R.id.item_news_tv_preview);
            categoryView = itemView.findViewById(R.id.item_news_tv_category);
            titleView = itemView.findViewById(R.id.item_news_tv_title);
            publishDateView = itemView.findViewById(R.id.item_news_tv_publish_date);

        }

        private void bind(@NonNull NewsItem newsItem) {
            imageLoader.load(newsItem.getImageUrl()).into(photoView);
            previewView.setText(newsItem.getPreviewText());
            categoryView.setText(newsItem.getCategory().getName());
            titleView.setText(newsItem.getTitle());
            publishDateView.setText(DateFormatUtils.getRelativeDateTime(itemView.getContext(),
                    newsItem.getPublishDate()));
        }
    }
}
