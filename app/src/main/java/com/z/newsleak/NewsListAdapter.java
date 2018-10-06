package com.z.newsleak;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.z.newsleak.data.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    @NonNull
    private final List<NewsItem> newsItems;
    @NonNull
    private final LayoutInflater inflater;
    @Nullable
    private final OnItemClickListener clickListener;
    @NonNull
    private final RequestManager imageLoader;


    public NewsListAdapter(@NonNull Context context, @NonNull List<NewsItem> newsItems,
                           @Nullable OnItemClickListener clickListener) {
        this.newsItems = newsItems;
        this.clickListener = clickListener;
        this.inflater = LayoutInflater.from(context);

        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.preview_placeholder)
                .fallback(R.drawable.preview_placeholder)
                .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView photoView;
        private final TextView previewView;
        private final TextView categoryView;
        private final TextView titleView;
        private final TextView publishDateView;

        public ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(newsItems.get(position));
                }
            });

            photoView = itemView.findViewById(R.id.item_news_iv_photo);
            previewView = itemView.findViewById(R.id.item_news_tv_preview);
            categoryView = itemView.findViewById(R.id.item_news_tv_category);
            titleView = itemView.findViewById(R.id.item_news_tv_title);
            publishDateView = itemView.findViewById(R.id.item_news_tv_publish_date);

        }

        void bind(@NonNull NewsItem newsItem) {
            imageLoader.load(newsItem.getImageUrl()).into(photoView);
            previewView.setText(newsItem.getPreviewText());
            categoryView.setText(newsItem.getCategory().getName());
            titleView.setText(newsItem.getTitle());
            CharSequence relativeDate;
            relativeDate = DateUtils.getRelativeTimeSpanString(newsItem.getPublishDate().getTime(),
                    System.currentTimeMillis(),
                    DateUtils.DAY_IN_MILLIS);
            publishDateView.setText(relativeDate);
        }
    }
}
