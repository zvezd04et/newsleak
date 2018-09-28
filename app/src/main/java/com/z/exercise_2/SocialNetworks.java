package com.z.exercise_2;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public enum SocialNetworks {

    GITHUB("https://github.com/zvezd04et/"),
    TELEGRAM("https://t.me/pantusov/", new String[]{"org.telegram.messenger", "org.thunderdog.challegram"}),
    VK("https://vk.com/id1748403", new String[]{"com.vkontakte.android"});

    private final String[] appPackages;
    private final String url;

    SocialNetworks(@NonNull String url) {
        this.appPackages = null;
        this.url = url;
    }

    SocialNetworks(@NonNull String url, @NonNull String[] appPackages) {
        this.appPackages = appPackages;
        this.url = url;
    }

    @Nullable
    public String[] getAppPackages() {
        return appPackages;
    }

    @NonNull
    public String getUrl() {
        return url;
    }
}
