package com.z.newsleak.data;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class PreferencesManager {

    private static final String SHARED_PREF = "com.z.newsleak.SHARED_PREF";
    private static final String SHARED_PREF_KEY_INTRO_VISIBILITY = SHARED_PREF + ".KEY_INTRO_VISIBILITY";

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

    public boolean isIntroVisible() {

        boolean isIntroVisible = sharedPreferences.getBoolean(SHARED_PREF_KEY_INTRO_VISIBILITY, true);

        sharedPreferences.edit()
                .putBoolean(SHARED_PREF_KEY_INTRO_VISIBILITY, !isIntroVisible)
                .apply();

        return isIntroVisible;
    }

}
