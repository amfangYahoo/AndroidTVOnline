package com.dev.rxnetmodule.base;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

/**
 * application
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
