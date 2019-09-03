package com.retrofit_test.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * fileDesc
 * Created by huanghp on 2019/9/3.
 * Email h1132760021@sina.com
 */
public interface RxApi {
    /**
     * 直接返回一段字符串
     */
    @GET("getAString")
    Observable<String> getAString();
}
