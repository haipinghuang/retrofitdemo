package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.Hcall;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 此中的接口都已测试 通过
 * Created by 黄海 on 4/8/2017.
 */
public interface HApi {
    String baseUrl = "http://10.200.6.38:8080/retrofitweb/";

    @GET("getNullUrl")
    Hcall<String> getNullUrl(@Header("key-header") String header);//请求无效网址

    @GET("getUser")
    Hcall<User> getUser();

    //请求服务器延迟delaySecond返回
    @GET("getDelay/{delaySecond}")
    Hcall<String> getDelay(@Path("delaySecond") int delaySecond);

    @GET("getValidatedUser")
    Hcall<BaseResponse<User>> getValidatedUser(@Query("userName") String userName, @Query("passwrod") String passwrod);

    @GET("getValidatedUsers")
    Hcall<BaseResponse<List<User>>> getValidatedUsers(@Query("userName") String... names);

    @FormUrlEncoded
    @POST("postUser")
    Hcall<User> postUser(@Field("name") String name);

    @POST("postUserName/{name}")
    Hcall<User> postUserName(@Path("name") String name);

    @FormUrlEncoded
    @POST("postUsers")
    Hcall<BaseResponse<List<User>>> postUsers(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("file/uploadFile")
    Hcall<String> uploadFile(@Part MultipartBody.Part file);//已ok

    //uploadFileWithUsername(@RequestParam("file") CommonsMultipartFile file,@RequestParam("name") String name)
    @POST("uploadFileWithName")
    @Multipart
    Hcall<BaseResponse<String>> uploadFileWithName(@Part MultipartBody.Part part, @Part("name") String name);//已ok

    @POST("uploadFileWithName")
    @Multipart
    Hcall<String> uploadFileReturnString(@Part MultipartBody.Part part);//

    @GET("downloadFile")
    @Streaming
    Hcall<ResponseBody> downloadFile(@Query("fileName") String filename);

    @GET
    @Streaming
    Hcall<ResponseBody> download(@Url String url);
}
