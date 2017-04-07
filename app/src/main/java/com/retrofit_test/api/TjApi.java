package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.Hcall;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 黄海 on 2016/12/9.
 */

public interface TjApi {
    String baseUrl = "http://127.0.0.109:8080/";

    @GET("getNullUrl")
    Hcall<String> getNullUrl();//请求无效网址

    @GET("tj/getString")
    Hcall<BaseResponse<String>> getString(@Query("p") String p);

    @GET("tj/getUser")
    Hcall<BaseResponse<User>> getUser(@Query("p") String p);

    @GET("tj/getUserList")
    Hcall<BaseResponse<List<User>>> getUserList(@Query("p") String p);

    @POST(value = "uploadFile2.do")
    Hcall<String> uploadFile();
}
