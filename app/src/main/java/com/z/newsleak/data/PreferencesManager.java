package com.z.newsleak.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferencesManager {

    private static final String SHARED_PREF = "com.z.newsleak.SHARED_PREF";
    private static final String KEY_IS_FIRST_TIME_LAUNCH = SHARED_PREF + ".KEY_IS_FIRST_TIME_LAUNCH";

    private final SharedPreferences sharedPreferences;
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

}
