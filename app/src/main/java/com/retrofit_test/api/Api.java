package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.ClaimsRecordKzr;
import com.retrofit_test.bean.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("getUserNull")
    Call<User> getUserNull();

    @GET("getUserList")
    Call<List<User>> getUserList();

    //url likes https://api.stay4it.com/tasks?id=123&id=124&id=125
    @GET("getUserList2")
    Call<List<User>> getUserList2(@Query("id") List<Integer> ids);

    //适用于参数较多的情况
    @POST("getUserList3")
    @FormUrlEncoded
    Call<List<User>> getUserList2(@FieldMap Map<String, String> map);

    @GET("getUserListNull")
    Call<List<User>> getUserListNull();

    @GET("getUser")
    Call<User> getUser(@Query("username") String uname, @Query("password") String pwd);

    @POST("postUser")
    @FormUrlEncoded
    Call<User> postUser(@Field("username") String uname, @Field("password") String pwd);

    @POST("postUser2")
    @FormUrlEncoded
    Call<User> postUser2(@Body User user);

    @GET("group/{id}/users")
    Call<List<User>> groupList(@Path("id") int groupId);

    @POST("users/new")
    Call<User> createUser(@Body User user);

    @Multipart
    @POST("file/uploadFile2.do")
    Call<User> uploadFile(@Part MultipartBody.Part file);//已ok

    @Multipart
    @POST("file/uploadFile2.do")
    Call<User> uploadFile(@Part MultipartBody.Part file, @Part("description") RequestBody description);//已ok

    @Multipart
    @POST("file/uploadFileWithUsername.do")
    Call<User> uploadFileWithUsername(@Part MultipartBody.Part file, @Part("userName") String uname);//已ok

    //*****************************************
    @GET("tjhf/loginRegist/login")
    Call<BaseResponse<Integer>> login(@Query("uname") String name, @Query("pwd") String password);

    @GET("tjhf/loginRegist/logout")
    Call<BaseResponse<Object>> loginOut();

    @GET("tjhf/claims/canTransfer/1/10")
    Call<BaseResponse<List<ClaimsRecordKzr>>> canTransfer();
}
