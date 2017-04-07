package com.retrofit_test.http;

import com.retrofit_test.util.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 黄海 on 2017/1/23.
 */

public class LoggingIntercepror implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.i(String.format(request.method() + ",发送请求:%s on %s %s", request.url(), request.headers(), chain.connection()));
        Response response = chain.proceed(request);

        MediaType contentType = response.body().contentType();
        String responseBody = response.body().string();
        Logger.i("响应结果:", "code=" + response.code() + " || isSuccessful=" + response.isSuccessful() + " || message=" + response.message() + "\n|| responseBody=" + responseBody);
        ResponseBody body = ResponseBody.create(contentType, responseBody);
        return response.newBuilder().body(body).build();
    }
}