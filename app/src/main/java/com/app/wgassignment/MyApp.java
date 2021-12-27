package com.app.wgassignment;

import android.app.Application;

import com.app.wgassignment.util.SharedPrefs;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new SharedPrefs(getApplicationContext());
    }
}
