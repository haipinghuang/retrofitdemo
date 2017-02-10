package com.retrofit_test.base;

import android.app.Application;

import com.retrofit_test.util.ApiUtils;

/**
 * Created by 黄海 on 2017/2/7.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiUtils.init(this);
    }
}
