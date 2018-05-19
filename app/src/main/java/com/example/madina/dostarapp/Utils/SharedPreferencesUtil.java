package com.example.madina.dostarapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtil {
    private static final String USER_PREFERENCES = "userPreferences";
    private static final String LANGUAGE = "language";
    private static final String TUTORIAL_IS_SHOWN = "tutorialIsShown";

    public static void setTutorialShown(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(TUTORIAL_IS_SHOWN, true);
        editor.apply();
    }

    public static Boolean isTutorialShown(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getBoolean(TUTORIAL_IS_SHOWN, false);
    }

    public static void setLanguage(Context context, String language) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences.getString(LANGUAGE, "en");
    }
}
