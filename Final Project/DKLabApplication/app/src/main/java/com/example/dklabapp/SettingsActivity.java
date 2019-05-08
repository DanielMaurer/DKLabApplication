package com.example.dklabapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);

        super.onCreate(savedInstanceState);
        setTheme(Settings.DEFAULT_THEME);
        setContentView(R.layout.activity_settings);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Settings.setPreferences(this);

        if (s.equals(getString(R.string.pref_theme_key))) {
            recreate();
        }
    }
}
