package com.edu.io.pulse.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
    private static final String PREF_NAME = "AppPreferences";
    private static AppSharedPreference instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private AppSharedPreference(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized AppSharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new AppSharedPreference(context);
        }
        return instance;
    }


    // Save a string value
    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    // Retrieve a string value
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Save an integer value
    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    // Retrieve an integer value
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Save a boolean value
    public void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Retrieve a boolean value
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Save a long value
    public void saveLong(String key, long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    // Retrieve a long value
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    // Remove a specific key-value pair
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    // Clear all preferences
    public void clear() {
        editor.clear();
        editor.apply();
    }
}
