package com.z.newsleak.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.z.newsleak.model.Category;
import com.z.newsleak.utils.NewsTypeConverters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PreferencesManager {

    private static final String SHARED_PREF = "com.z.newsleak.SHARED_PREF";
    private static final String KEY_IS_FIRST_TIME_LAUNCH = SHARED_PREF + ".KEY_IS_FIRST_TIME_LAUNCH";
    private static final String KEY_CURRENT_CATEGORY = SHARED_PREF + ".KEY_CURRENT_CATEGORY";

    @NonNull
    private final SharedPreferences sharedPreferences;
    @Nullable
    private static PreferencesManager manager;

    @NonNull
    public static PreferencesManager getInstance(@NonNull Context context) {
        if (manager == null) {
            synchronized (PreferencesManager.class) {
                if (manager == null) {
                    manager = new PreferencesManager(context.getApplicationContext());
                }
            }
        }
        return manager;
    }

    private PreferencesManager(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        sharedPreferences.edit()
                .putBoolean(KEY_IS_FIRST_TIME_LAUNCH, isFirstTime)
                .apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(KEY_IS_FIRST_TIME_LAUNCH, true);
    }

    public void setCurrentCategory(@Nullable Category category) {
        sharedPreferences.edit()
                .putString(KEY_CURRENT_CATEGORY, NewsTypeConverters.fromCategory(category))
                .apply();
    }

    @Nullable
    public Category getCurrentCategory() {
        final String section = sharedPreferences.getString(KEY_CURRENT_CATEGORY, null);
        return NewsTypeConverters.toCategory(section);
    }
}
