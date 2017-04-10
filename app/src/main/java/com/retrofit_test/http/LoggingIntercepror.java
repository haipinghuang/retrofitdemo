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
        StringBuilder stringBuilder = new StringBuilder("code=" + response.code() + " || isSuccessful=" + response.isSuccessful() + " || message=" + response.message() + " || contentType=" + contentType);
        if (!contentType.toString().contains("octet-stream"))//such as contentType=application/octet-stream
            stringBuilder.append(" ||\nresponseBody=" + responseBody);
        Logger.i("响应结果:", stringBuilder.toString());
        ResponseBody body = ResponseBody.create(contentType, responseBody);
        return response.newBuilder().body(body).build();
    }
}