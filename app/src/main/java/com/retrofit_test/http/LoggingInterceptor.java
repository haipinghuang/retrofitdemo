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

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String requsetBody = request.body().toString();
        Logger.i(String.format(request.method() + ",发送请求:%s requestBody= %s", request.url(), requsetBody));
        Response response = chain.proceed(request);

        ResponseBody body;
        MediaType contentType = response.body().contentType();
        StringBuilder stringBuilder = new StringBuilder("code=" + response.code() + " || isSuccessful=" + response.isSuccessful() + " || message=" + response.message() + " || contentType=" + contentType);
        if (contentType != null && !contentType.toString().contains("octet-stream") && !contentType.toString().contains("package-archive")) { //such as contentType=application/octet-stream
            String responseBody = response.body().string();
            stringBuilder.append(" ||\nresponseBody=" + responseBody);
            body = ResponseBody.create(contentType, responseBody);
        } else
            body = response.body();
        Logger.i("响应结果:" + stringBuilder.toString());
        return response.newBuilder().body(body).build();
    }
}