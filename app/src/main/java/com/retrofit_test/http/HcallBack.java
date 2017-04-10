package com.retrofit_test.http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 黄海 on 2017/4/7.
 */

public interface HcallBack<T> extends Callback<T> {
    void onStart();

    /**
     * it is used when response.isSuccessful() and response.code()==200
     * @param call
     * @param response
     */
    void onSuccess(Call<T> call, Response<T> response);

    void onCompleted();
}
