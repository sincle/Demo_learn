package com.haieros.demo_learn.app;

import android.app.Application;

import com.haieros.demo_learn.crash.CrashHandler;

/**
 * Created by Kang on 2018/1/10.
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
