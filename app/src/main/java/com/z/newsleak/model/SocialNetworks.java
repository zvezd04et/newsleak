package com.z.newsleak.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum SocialNetworks {

    GITHUB("https://github.com/zvezd04et/"),
    TELEGRAM("https://t.me/pantusov/", new String[]{"org.telegram.messenger", "org.thunderdog.challegram"}),
    VK("https://vk.com/id1748403", new String[]{"com.vkontakte.android"});

    @Nullable
    private final String[] appPackages;
    @NonNull
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
