package com.retrofit_test.http;

import retrofit2.Call;

/**
 * Created by 黄海 on 2017/4/7.
 */

public abstract class BaseCallBack<T> implements HcallBack<T> {
    @Override
    public void onStart() {

    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    @Override
    public void onCompleted() {

    }
}
