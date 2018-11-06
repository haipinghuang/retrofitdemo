package com.retrofit_test.http;

import android.support.annotation.Nullable;

import com.retrofit_test.util.Logger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * log for print request info and response info,like requestHeader,requester parameters,and response body
 * Created by huanghp on 2018/9/29.
 * Email h1132760021@sina.com
 */
public class LoggingInterceptor implements Interceptor {
    /**
     * 是否打印request Header
     */
    private boolean printrRequesterHeader = false;
    /**
     * 是否打印Respons Header
     */
    private boolean printrResponseHeader = true;

    public LoggingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = printRequest(request, printrRequesterHeader);

        Response response = chain.proceed(requestBuilder != null ? requestBuilder.build() : request);
        ResponseBody body = printResponse(response,printrResponseHeader);
        return response.newBuilder().body(body).build();
    }

    /**
     * printResponse
     *
     * @param response
     * @param printHeader
     * @return
     * @throws IOException
     */
    private ResponseBody printResponse(Response response, boolean printHeader) throws IOException {
        ResponseBody body;
        MediaType contentType = response.body().contentType();
        StringBuilder stringBuilder = new StringBuilder("code=" + response.code() + " || isSuccessful=" + response.isSuccessful() + " || message=" + response.message() + " || contentType=" + contentType);
        if (printHeader) {
            Headers headers = response.headers();
            if (printHeader && headers != null) {//默认不打印requestHeader
                stringBuilder.append(" || requestHeader=" + headers.toString());
            }
        }
        if (contentType != null && !contentType.toString().contains("octet-stream") && !contentType.toString().contains("package-archive")) { //such as contentType=application/octet-stream
            String responseBody = response.body().string();
            stringBuilder.append(" ||\nresponseBody=" + responseBody);
            body = ResponseBody.create(contentType, responseBody);
        } else {
            body = response.body();
        }
        Logger.i("响应结果:" + stringBuilder.toString());
        return body;
    }

    /**
     * printRequest
     *
     * @param request
     * @param printHeader
     * @return
     */
    @Nullable
    private Request.Builder printRequest(Request request, boolean printHeader) {
        StringBuilder stringBuilder = new StringBuilder(request.method() + " " + request.url());
        if (printHeader) {
            Headers headers = request.headers();
            if (printHeader && headers != null) {//默认不打印requestHeader
                stringBuilder.append(" || requestHeader=" + headers.toString());
            }
        }
        RequestBody requestBody = request.body();
        Request.Builder requestBuilder = null;
        if (requestBody != null) {
            /**仿照{@linkplain okhttp3.RealCall.getResponse()}的做法*/
            requestBuilder = request.newBuilder();
            MediaType contentType = requestBody.contentType();
            if (contentType != null && !contentType.toString().contains("octet-stream") && !contentType.toString().contains("package-archive") && !contentType.toString().contains("multipart")) {
                appendRequestBody(stringBuilder, requestBody);
            }
            stringBuilder.append(" || contentType=" + contentType);
        }
        Logger.i("发送请求:" + stringBuilder.toString());
        return requestBuilder;
    }

    /**
     * 拼装requestBody
     *
     * @param stringBuilder
     * @param requestBody
     */
    private void appendRequestBody(StringBuilder stringBuilder, RequestBody requestBody) {
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
            String body = buffer.toString();
            body = body.replaceFirst("text=", "");
            stringBuilder.append(" || requestBody=" + body);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            buffer.close();
        }
    }
}