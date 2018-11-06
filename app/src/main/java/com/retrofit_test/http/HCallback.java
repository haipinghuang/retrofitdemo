package com.retrofit_test.http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * callback for request，method onResponse will be called when request is sended to server seccessfully;
 * otherwise onFailure will be called;
 * the call lifecycle will be one of borrow:
 * 1:   onStart->onResponse->onSuccess->onCompleted
 * 2:   onStart->onResponse->onSuccess->onFailure->onCompleted
 * 3:   onStart->onFailure->onCompleted
 * Created by huanghp on 2018/9/28.
 * Email h1132760021@sina.com
 */
public interface HCallback<T> extends Callback<T> {
    /**
     * called on mainThread
     */
    void onStart();

    /**
     * it is used when response.isSuccessful() and response.code()==200
     * called on mainThread
     *
     * @param call
     * @param response
     */
    void onSuccess(Call<T> call, Response<T> response);

    /**
     * called on mainThread
     */
    void onCompleted();
}
