package com.z.newsleak;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.z.newsleak.data.DataUtils;
import com.z.newsleak.data.NewsItem;
import com.z.newsleak.utils.DateFormatUtils;
import com.z.newsleak.utils.ImageLoadUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final static int DEFAULT_VIEW = 0;
    private final static int ANIMAL_VIEW = 1;

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
        this.imageLoader = ImageLoadUtils.getImageLoader(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutRes = 0;
        switch (viewType) {
            case ANIMAL_VIEW:
                layoutRes = R.layout.animal_item_news;
                break;
            case DEFAULT_VIEW:
                layoutRes = R.layout.default_item_news;
                break;

            default:
                Log.d("ViewType", "Get unknown view type");
        }
        return new ViewHolder(inflater.inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(newsItems.get(position));
    }

    @NonNull
    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    @Override
    public int getItemViewType(@NonNull int position) {

        switch(newsItems.get(position).getCategory().getId()) {
            case DataUtils.ANIMAL_ID:
                return ANIMAL_VIEW;
            default:
                return DEFAULT_VIEW;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull NewsItem newsItem);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
            publishDateView.setText(DateFormatUtils.getRelativeDateWithMinutes(newsItem.getPublishDate()));
        }
    }
}
