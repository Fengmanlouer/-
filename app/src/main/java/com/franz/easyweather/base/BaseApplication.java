package com.franz.easyweather.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    //Do not place Android context classes in static fields;this is a memory leak:
    // 不要将Android上下文类放在静态字段中.这是内存泄漏
    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
