package com.z.newsleak.utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

import com.z.newsleak.App;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class NetworkUtils {

    @Nullable
    private ConnectivityManager cm;

    @NonNull
    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(Network network) {
            networkState.onNext(isNetworkAvailable());
        }

        @Override
        public void onLost(Network network) {
            networkState.onNext(isNetworkAvailable());
        }
    };

    @NonNull
    private Subject<Boolean> networkState = BehaviorSubject.createDefault(isNetworkAvailable());

    @Nullable
    private static NetworkUtils networkUtils;

    @NonNull
    public static NetworkUtils getInstance(ConnectivityManager cm) {
        if (networkUtils == null) {
            synchronized (NetworkUtils.class) {
                if (networkUtils == null) {
                    networkUtils = new NetworkUtils(cm);
                }
            }
        }
        return networkUtils;
    }

    @Inject
    public NetworkUtils(@Nullable ConnectivityManager cm) {
        this.cm = cm;
    }

    @NonNull
    public ConnectivityManager.NetworkCallback getNetworkCallback() {
        return networkCallback;
    }

    @NonNull
    public Single<Boolean> getOnlineNetwork() {
        return networkState
                .subscribeOn(Schedulers.io())
                .filter(online -> online)
                .firstOrError();
    }

    private boolean isNetworkAvailable() {

        if (cm == null) {
            return false;
        }
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }

    public void registerNetworkCallback() {
        if (cm != null) {
            cm.registerNetworkCallback(new NetworkRequest.Builder().build(),
                    getNetworkCallback());
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            networkState.onNext(isNetworkAvailable());
        }

    }
}
