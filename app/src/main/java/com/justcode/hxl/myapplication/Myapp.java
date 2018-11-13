package com.justcode.hxl.myapplication;

import android.app.Application;

import com.justcode.hxl.localstorage.sharedpreferences.PropertyBySharedPref;

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PropertyBySharedPref.Companion.setContext0(this);
    }
}
