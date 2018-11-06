package com.retrofit_test.http;

import com.retrofit_test.util.Logger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 黄海 on 2016/12/26.
 */

public class SimpleOkHttpCallBack implements Callback {
    @Override
    public void onFailure(Call call, IOException e) {
        Logger.e(call.toString(), e.getLocalizedMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Logger.e(call.request().url().toString(), response.toString() + ",return content:" + response.body().string());
        if (response.body() != null) response.body().close();
    }

    /**
     * @param sended 已上传
     * @param total  总长度
     */
    public void onProgress(long sended, long total) {

    }
}