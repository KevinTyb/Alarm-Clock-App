package com.example.alarmclock;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences sharedPreferences;
    public SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    //Saving the dark mode state when app is off
    public void DarkModeState(Boolean state) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DarkMode", state);
        editor.apply();
    }

    //load the Dark Mode state
    public Boolean loadDarkMode() {
        return sharedPreferences.getBoolean("DarkMode", false);
    }
}
