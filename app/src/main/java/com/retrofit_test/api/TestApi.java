package com.retrofit_test.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 黄海 on 2016/12/9.
 */

public interface TestApi {
    @GET("tjhf/loginRegist/login")
    Call<String> getUser(@Query("username") String uname, @Query("password") String pwd);
}
