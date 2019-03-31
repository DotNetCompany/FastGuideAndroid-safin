package com.fast.guide.Utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class settings extends Application {

    private static settings mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static settings getContext() {
        return mContext;
    }

    private Context CONTEXT;
    public settings(Context context){
        this.CONTEXT=context;
    }

    public settings(){
        this.CONTEXT=this.getApplicationContext();
    }


    public void setValue(String key, String value  ){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CONTEXT);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public String getValue( String key){
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(CONTEXT);
        return prefs1.getString(key,"");
    }



    public void setValue(String key, int value  ){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(CONTEXT);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public int getIntValue( String key){
        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(CONTEXT);
        return prefs1.getInt(key,0);
    }
}
