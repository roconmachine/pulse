package com.edu.io.pulse.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AppSharedPreference {
    private static final String PREF_NAME = "AppPreferences";
    private static AppSharedPreference instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private AppSharedPreference(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public static synchronized AppSharedPreference getInstance(Context context) {
        if (instance == null) {
            instance = new AppSharedPreference(context);
        }
        return instance;
    }

    /**
     * Save any Object (POJO, Parcelable, etc.) by converting it to JSON.
     */
    public <T> void saveObject(String key, T object) {
        String json = gson.toJson(object);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Retrieve an Object from JSON.
     */
    public <T> T getObject(String key, Class<T> clazz) {
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    /**
     * Save a List of Objects by converting it to JSON.
     */
    public <T> void saveList(String key, List<T> list) {
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * Retrieve a List of Objects from JSON.
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
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
