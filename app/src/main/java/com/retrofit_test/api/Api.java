package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.ClaimsRecordKzr;
import com.retrofit_test.bean.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 黄海 on 2017/2/7.
 */

public interface Api {

    @GET("getException")
    Call<String> getException();//请求无效网址

    @GET("getCommonText")
    Call<String> getCommonText();

    @GET("getUserReturnNull")
    Call<User> getUserReturnNull();

    @GET("getUserList")
    Call<List<User>> getUserList();

    @GET("getUserListNull")
    Call<List<User>> getUserListNull();

    @GET("getUser")
    Call<User> getUser(@Query("username") String uname, @Query("password") String pwd);

    @POST("postUser")
    @FormUrlEncoded
    Call<User> postUser(@Field("username") String uname, @Field("password") String pwd);

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);

    @POST("users/new")
    Call<User> createUser(@Body User user);

    @POST("file/uploadFile2.do")
    Call<User> uploadFile(@Body RequestBody body);

    @FormUrlEncoded
    @POST("file/uploadFileWithUsername.do")
    Call<User> uploadFileWithUsername(@Field("file") RequestBody body, @Field("userName") String uname);

    //*****************************************
    @GET("tjhf/loginRegist/login")
    Call<BaseResponse<Integer>> login(@Query("uname") String name, @Query("pwd") String password);

    @GET("tjhf/loginRegist/logout")
    Call<BaseResponse<Object>> loginOut();

    @GET("tjhf/claims/canTransfer/1/10")
    Call<BaseResponse<List<ClaimsRecordKzr>>> canTransfer();
}
