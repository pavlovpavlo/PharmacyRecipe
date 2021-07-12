package com.sklad.er71.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class LocalSharedUtil {
    private static final String LOCAL_STORAGE_TOKEN_PARAMETER = "snils_er71_parameter";
    private static final String LOCAL_STORAGE_NAME_PARAMETER = "name_er71_parameter";
    private static final String LOCAL_STORAGE_SURNAME_PARAMETER = "surname_er71_parameter";
    private static final String LOCAL_STORAGE_BIRTHDAY_PARAMETER = "birthday_er71_parameter";

    public static void setSnilsParameter(String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOCAL_STORAGE_TOKEN_PARAMETER, value);
        editor.apply();
    }

    public static String getSnilsParameter(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LOCAL_STORAGE_TOKEN_PARAMETER, "");
    }

    public static void setNameParameter(String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOCAL_STORAGE_NAME_PARAMETER, value);
        editor.apply();
    }

    public static String getNameParameter(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LOCAL_STORAGE_NAME_PARAMETER, "");
    }

    public static void setSurnameParameter(String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOCAL_STORAGE_SURNAME_PARAMETER, value);
        editor.apply();
    }

    public static String getSurnameParameter(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LOCAL_STORAGE_SURNAME_PARAMETER, "");
    }

    public static void setBirthdayParameter(String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOCAL_STORAGE_BIRTHDAY_PARAMETER, value);
        editor.apply();
    }

    public static String getBirthdayParameter(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(LOCAL_STORAGE_BIRTHDAY_PARAMETER, "");
    }


}
