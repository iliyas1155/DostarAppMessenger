package com.example.madina.dostarapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {
    private static final String LANGUAGE = "language";

    public static void setLanguage(Context context, String language) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(LANGUAGE, MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE, "en");
    }
}
