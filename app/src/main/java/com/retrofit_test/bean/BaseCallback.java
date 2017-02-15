package com.retrofit_test.bean;

import android.content.Context;
import android.widget.Toast;

import com.retrofit_test.exception.ClientException;
import com.retrofit_test.exception.ServerException;
import com.retrofit_test.util.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 黄海 on 2017/2/8.
 */

public abstract class BaseCallback<T> implements Callback<T> {
    private static final String TAG = "BaseCallback";
    private Context context;

    public BaseCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Logger.i(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
        if (response.code() == 200 && response.isSuccessful() == true) {
            try {
                onSuccess(call, response);
            } catch (Exception e) {
                e.printStackTrace();
                onFailure(call, new ClientException("onSuccess 方法错误", e));
            }
        } else onFailure(call, new ServerException("服务器错误"));

    }

    protected abstract void onSuccess(Call<T> call, Response<T> response);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Logger.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
        if (t instanceof ServerException || t instanceof ClientException) {
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (t instanceof UnknownHostException || t instanceof SocketTimeoutException || t instanceof ConnectException) {
            Toast.makeText(context, "网络错误，请稍候重试", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "未知错误，请稍候重试", Toast.LENGTH_SHORT).show();
        }
    }
}
