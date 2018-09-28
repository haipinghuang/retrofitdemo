package com.retrofit_test.http;

import android.content.Context;
import android.widget.Toast;

import com.retrofit_test.exception.ClientException;
import com.retrofit_test.exception.ServerException;
import com.retrofit_test.util.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 黄海 on 2017/4/7.
 */

public abstract class BaseCallback<T> implements HCallback<T> {
    private static final String TAG = "BaseCallback";
    protected Context context;

    public BaseCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        Logger.i(TAG, "onStart() called");
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && response.code() == 200) {
            try {
                onSuccess(call, response);
            } catch (Exception e) {
                onFailure(call, new ClientException("onSuccess方法出错，请检查", e));
            }
        } else {
            onFailure(call, new ServerException("服务器内部错误，请稍候重试"));
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Logger.e(TAG, "onFailure() called with: call = [" + call + "],\n t = [" + t + "]");
        if (t instanceof ServerException || t instanceof ClientException) {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (t instanceof UnknownHostException || t instanceof SocketTimeoutException || t instanceof ConnectException) {
            Toast.makeText(context, "网络错误，请稍候重试", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "未知错误，请稍候重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompleted() {
        Logger.i(TAG, "onCompleted() called");
    }
}
