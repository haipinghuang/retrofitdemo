package com.retrofit_test.api;

import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.Mixed;
import com.retrofit_test.bean.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 黄海 on 2016/12/9.
 */

public interface RxApi {

    @GET("tj/getMixedNull")
    Call<BaseResponse<Mixed>> getMixedNull(@Query("p") String p);


    @GET("tj/getUser")
    Call<BaseResponse<User>> getUser(@Query("p") String p);

    @GET("tj/getUserList")
    Call<BaseResponse<List<User>>> getUserList(@Query("p") String p);

    @POST(value = "uploadFile2.do")
    Call<String> uploadFile();
}
