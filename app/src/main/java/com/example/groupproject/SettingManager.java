package com.example.groupproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingManager {
    private static final String PREF_NAME = "UserSettings";
    private static final String DARK_MODE = "dark_mode";
    private static final String FONT_SIZE = "font_size";
    private static final String FONT_STYLE = "font_style";

    private final SharedPreferences prefs;

    public SettingManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setDarkMode(boolean isEnabled) {
        prefs.edit().putBoolean(DARK_MODE, isEnabled).apply();
    }

    public boolean isDarkMode() {
        return prefs.getBoolean(DARK_MODE, false);
    }

    public void setFontSize(String size) {
        prefs.edit().putString(FONT_SIZE, size).apply();
    }

    public String getFontSize() {
        return prefs.getString(FONT_SIZE, "Medium");
    }

    public void setFontStyle(String style) {
        prefs.edit().putString(FONT_STYLE, style).apply();
    }

    public String getFontStyle() {
        return prefs.getString(FONT_STYLE, "Default");
    }
}
