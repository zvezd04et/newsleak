package com.z.newsleak.features.news_details;

import android.graphics.Bitmap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.z.newsleak.ui.LoadState;
import com.z.newsleak.ui.LoadingScreenHolder;

import androidx.annotation.NonNull;

public class NewsWebViewClient extends WebViewClient {

    @NonNull
    private LoadingScreenHolder loadingScreen;
    private boolean receivedError;

    public NewsWebViewClient(@NonNull LoadingScreenHolder loadingScreen) {
        this.loadingScreen = loadingScreen;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        receivedError = false;
        loadingScreen.showState(LoadState.LOADING);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        receivedError = true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (receivedError) {
            loadingScreen.showState(LoadState.ERROR);
            return;
        }
        loadingScreen.showState(LoadState.HAS_DATA);
    }
}
