package com.app.wgassignment.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.app.wgassignment.R;


public class SharedPrefs {
    Context context;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    public SharedPrefs(Context context)
    {
        this.context=context;
        sharedPreferences= context.getSharedPreferences(context.getString(R.string.covid),Context.MODE_PRIVATE);
    }
    public static void setString(String key,String value)
    {
        editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key)
    {
        String s=sharedPreferences.getString(key,"");
        return s;
    }

    public static void setInt(String key,int value)
    {
        editor=sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static int getInt(String key)
    {
        int s=sharedPreferences.getInt(key,0);
        return s;
    }
    public static void setBoolean(String key,boolean value)
    {
        editor=sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
    public static boolean getBoolean(String key)
    {
        boolean s=sharedPreferences.getBoolean(key,false);
        return s;
    }
    public static void clear()
    {
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
