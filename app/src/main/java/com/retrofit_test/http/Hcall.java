package com.retrofit_test.http;

import retrofit2.Call;

/**
 * 用于定义api的返回参数
 * Created by 黄海 on 2017/4/7.
 */
public interface Hcall<T> extends Call<T> {
}
