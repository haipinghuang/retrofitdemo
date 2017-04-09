package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.Hcall;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by 黄海 on 4/8/2017.
 */

public interface RetrofitApi {
    @GET("getNullUrl")
    Hcall<String> getNullUrl();//请求无效网址

    @GET("getUser")
    Hcall<User> getUser();

    @GET("getValidatedUser")
    Hcall<BaseResponse<User>> getValidatedUser(@Query("userName") String userName, @Query("passwrod") String passwrod);

    @GET("getValidatedUsers")
    Hcall<BaseResponse<List<User>>> getValidatedUsers(@Query("userName") String... names);

    @POST("postUser")
    Hcall<User> postUser(@Field("name") String name);

    @POST("postUserName/{name}")
    Hcall<User> postUserName(@Path("name") String name);

    @FormUrlEncoded
    @POST("postUsers")
    Hcall<BaseResponse<List<User>>> postUsers(@FieldMap Map<String, String> fields);

    @POST("uploadFile")
    @Multipart
    Hcall<BaseResponse<String>> uploadFile(@Part MultipartBody.Part part, @Part("name") String name);

    @GET("downloadFile")
    @Streaming
    Hcall<String> downloadFile();
}
