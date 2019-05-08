package com.example.dklabapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    public static int DEFAULT_THEME = 0;

    public static void setPreferences(Activity activity){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        Boolean darkTheme = preferences.getBoolean(activity.getResources().getString(R.string.pref_theme_key), true);
        if(darkTheme){
            DEFAULT_THEME = R.style.AppThemeDark;
        }else{
            DEFAULT_THEME = R.style.AppTheme;
        }

    }
}
