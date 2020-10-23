package com.orion.stapoo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    public static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "com.orion.stapoo";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String IS_DETAILS_ENTERED = "isAvatarSelected";

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsAvatarChosen(boolean isDetailsEntered) {
        editor.putBoolean(IS_DETAILS_ENTERED, isDetailsEntered);
        editor.commit();
    }

    public boolean isAvatarChosen() {
        return pref.getBoolean(IS_DETAILS_ENTERED, false);
    }

    public void setAvatar(int num){
        editor.putInt("avatar", num);
        editor.commit();
    }



}
