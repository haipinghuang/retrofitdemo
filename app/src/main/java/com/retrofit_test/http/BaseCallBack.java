package com.retrofit_test.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.retrofit_test.util.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;

/**
 * Created by 黄海 on 2017/4/7.
 */

public abstract class BaseCallBack<T> implements HcallBack<T> {
    private static final String TAG = "BaseCallBack";
    protected Context context;

    public BaseCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        Logger.i(TAG, "onStart() called");
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Logger.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
        if (t instanceof UnknownHostException || t instanceof SocketTimeoutException || t instanceof ConnectException) {
            Toast.makeText(context, "网络错误，请稍候重试", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "未知错误，请稍候重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted() called");
    }
}
