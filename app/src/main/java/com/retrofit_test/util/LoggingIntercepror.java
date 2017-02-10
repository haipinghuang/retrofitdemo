package com.retrofit_test.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 黄海 on 2017/1/23.
 */

public class LoggingIntercepror implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.i(String.format("发送请求:%s on %s %s", request.url(), request.headers(), chain.connection()));
        Response response = chain.proceed(request);
        Logger.i("响应结果:", "code=" + response.code() + " || isSuccessful=" + response.isSuccessful() + " || message=" + response.message());
//        Logger.i("body="+new String(response.body().bytes()));
        return response;
    }
}