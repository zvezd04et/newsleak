package com.z.newsleak.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.z.newsleak.R;
import com.z.newsleak.data.LoadState;
import com.z.newsleak.utils.SupportUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LoadingScreenHolder {

    private static final String LOG_TAG = "LoadingScreenHolder";

    @NonNull
    private final View contentView;
    @NonNull
    private Activity context;
    @Nullable
    private ProgressBar progressBar;
    @Nullable
    private View errorContent;
    @Nullable
    private TextView tvError;

    public LoadingScreenHolder(@NonNull View contentView, @Nullable View.OnClickListener clickListener) {
        this.contentView = contentView;

        context = (Activity) contentView.getContext();
        progressBar = context.findViewById(R.id.news_list_progress);
        errorContent = context.findViewById(R.id.error_content);
        tvError = context.findViewById(R.id.error_tv_text);

        final Button retryBtn = context.findViewById(R.id.error_btn_retry);
        retryBtn.setOnClickListener(clickListener);
    }

    public void showState(@NonNull LoadState state) {

        switch (state) {
            case HAS_DATA:
                SupportUtils.setVisible(contentView, true);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, false);
                break;
            case HAS_NO_DATA:
                SupportUtils.setVisible(contentView, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                SupportUtils.setText(tvError, context.getText(R.string.error_loading_news_no_data));
                break;
            case NETWORK_ERROR:
                SupportUtils.setVisible(contentView, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                SupportUtils.setText(tvError, context.getText(R.string.error_loading_news_network));
                break;
            case SERVER_ERROR:
                SupportUtils.setVisible(contentView, false);
                SupportUtils.setVisible(progressBar, false);
                SupportUtils.setVisible(errorContent, true);
                SupportUtils.setText(tvError, context.getText(R.string.error_loading_news_server));
                break;
            case LOADING:
                SupportUtils.setVisible(contentView, false);
                SupportUtils.setVisible(progressBar, true);
                SupportUtils.setVisible(errorContent, false);
                break;

            default:
                Log.d(LOG_TAG, "Unknown state: " + state);
        }
    }
}
