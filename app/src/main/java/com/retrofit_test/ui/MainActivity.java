package com.retrofit_test.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.api.Api;
import com.retrofit_test.bean.BaseCallback;
import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.ClaimsRecordKzr;
import com.retrofit_test.bean.User;
import com.retrofit_test.util.ApiUtils;
import com.retrofit_test.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = ApiUtils.getRetrofit().create(Api.class);
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.temp:
                api.getUserList2(Arrays.asList(12, 23, 543)).enqueue(new BaseCallback<List<User>>(this) {
                    @Override
                    protected void onSuccess(Call<List<User>> call, Response<List<User>> response) throws Exception {

                    }
                });
                break;
            case R.id.uploadFileWithUsername:
                uploadFileWithUsername("huang");
                break;
            case R.id.uploadFile:
                uploadFile();
                break;
            case R.id.postUser:
                api.postUser("huang2", "hai2").enqueue(new BaseCallback<User>(this) {
                    @Override
                    protected void onSuccess(Call<User> call, Response<User> response) throws IOException {
                        Logger.e(response.body().getUsername() + ";;;;" + response.body().getPassword());
                    }
                });
                break;

            case R.id.getException:
                api.getException().enqueue(new BaseCallback<String>(this) {
                    @Override
                    protected void onSuccess(Call<String> call, Response<String> response) throws Exception {

                    }
                });
                break;
            case R.id.getUser:
                api.getUser("huang黄", "hai是对的").enqueue(new BaseCallback<User>(this) {
                    @Override
                    protected void onSuccess(Call<User> call, Response<User> response) throws IOException {
                        Logger.e(response.body().getUsername() + ";;;;" + response.body().getPassword());
                    }
                });
                break;
            case R.id.getUserReturnNull:
                api.getUserReturnNull().enqueue(new BaseCallback<User>(this) {
                    @Override
                    protected void onSuccess(Call<User> call, Response<User> response) throws IOException {
                        Logger.e(response.body().getUsername() + ";;;;" + response.body().getPassword());
                    }
                });
                break;
            case R.id.getUserListNull:
                api.getUserListNull().enqueue(new BaseCallback<List<User>>(this) {
                    @Override
                    protected void onSuccess(Call<List<User>> call, Response<List<User>> response) throws Exception {

                    }
                });
                break;
            case R.id.getUserList:
                api.getUserList().enqueue(new BaseCallback<List<User>>(this) {
                    @Override
                    protected void onSuccess(Call<List<User>> call, Response<List<User>> response) throws IOException {
                        for (User u : response.body()) {
                            Logger.e(u.getUsername() + ";;;;" + u.getPassword());
                        }
                    }
                });
                break;
        }
    }

    private void uploadFileWithUsername(String str) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "out.apatch");
        if (file.exists())
            Logger.i("file name=" + file.getName() + ",length=" + file.length());
        RequestBody requestfile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestfile);
        api.uploadFileWithUsername(part, str).enqueue(new BaseCallback<User>(this) {
            @Override
            protected void onSuccess(Call<User> call, Response<User> response) throws Exception {

            }
        });
    }

    private void uploadFile() {//测试350M文件可以上传
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Video/EP40.mp4");
        if (file.exists())
            Logger.i("file name=" + file.getName() + ",length=" + file.length());
        RequestBody requestfile = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "This is a description");
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestfile);
        api.uploadFile(part).enqueue(new BaseCallback<User>(this) {
            @Override
            protected void onSuccess(Call<User> call, Response<User> response) throws Exception {

            }
        });
    }

    private void canTransfer() {//测试350M文件可以上传
        api.canTransfer().enqueue(new BaseCallback<BaseResponse<List<ClaimsRecordKzr>>>(this) {
            @Override
            protected void onSuccess(Call<BaseResponse<List<ClaimsRecordKzr>>> call, Response<BaseResponse<List<ClaimsRecordKzr>>> response) {
                Logger.e(response.body().toString());
                Logger.e("list size=" + response.body().getData().size());
            }
        });
    }

    void login() {
        api.login("13265468238", "123456").enqueue(new BaseCallback<BaseResponse<Integer>>(this) {
            @Override
            protected void onSuccess(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) throws IOException {
                Logger.e(response.body().toString());
            }
        });
    }

    void loginOut() {
        api.loginOut().enqueue(new BaseCallback<BaseResponse<Object>>(this) {
            @Override
            protected void onSuccess(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {

            }
        });
    }
}
