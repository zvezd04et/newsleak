package com.z.newsleak.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.z.newsleak.R;
import com.z.newsleak.utils.ViewUtils;

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

        final View rootView = contentView.getRootView();
        progressBar = rootView.findViewById(R.id.news_list_progress);
        errorContent = rootView.findViewById(R.id.error_content);
        tvError = rootView.findViewById(R.id.error_tv_text);

        final Button retryBtn = rootView.findViewById(R.id.error_btn_retry);
        retryBtn.setOnClickListener(clickListener);
    }

    public void showState(@NonNull LoadState state) {

        switch (state) {
            case HAS_DATA:
                ViewUtils.setVisible(contentView, true);
                ViewUtils.setVisible(progressBar, false);
                ViewUtils.setVisible(errorContent, false);
                break;

            case HAS_NO_DATA:
                ViewUtils.setVisible(contentView, false);
                ViewUtils.setVisible(progressBar, false);
                ViewUtils.setVisible(errorContent, true);
                ViewUtils.setText(tvError, context.getText(R.string.state_loading_msg_no_data));
                break;

            case ERROR:
                ViewUtils.setVisible(contentView, false);
                ViewUtils.setVisible(progressBar, false);
                ViewUtils.setVisible(errorContent, true);
                ViewUtils.setText(tvError, context.getText(R.string.state_loading_msg_error));
                break;

            case LOADING:
                ViewUtils.setVisible(contentView, false);
                ViewUtils.setVisible(progressBar, true);
                ViewUtils.setVisible(errorContent, false);
                break;

            default:
                Log.d(LOG_TAG, "Unknown state: " + state);
        }
    }
}
