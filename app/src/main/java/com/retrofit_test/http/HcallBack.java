package com.retrofit_test.http;

import retrofit2.Callback;

/**
 * Created by 黄海 on 2017/4/7.
 */

public interface HcallBack<T> extends Callback<T> {
    void onStart();

    void onCompleted();
}
