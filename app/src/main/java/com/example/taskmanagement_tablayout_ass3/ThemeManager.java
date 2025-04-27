package com.example.taskmanagement_tablayout_ass3;

import android.app.Activity;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    public static void applyTheme(Activity activity) {
        SharedPrefManager prefManager = new SharedPrefManager(activity);

        String theme = prefManager.getTheme();
        if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
