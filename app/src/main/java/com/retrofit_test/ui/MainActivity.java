package com.retrofit_test.ui;

import android.os.Bundle;
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

import java.io.IOException;
import java.util.List;

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
            case R.id.getException:
                api.getException().enqueue(new BaseCallback<String>(this) {
                    @Override
                    protected void onSuccess(Call<String> call, Response<String> response) {

                    }
                });
                break;
            case R.id.getUser:
                api.getUser("huang", "hai").enqueue(new BaseCallback<User>(this) {
                    @Override
                    protected void onSuccess(Call<User> call, Response<User> response){
                        Logger.e(response.body().getUsername() + ";;;;" + response.body().getPassword());
                    }
                });
                break;
            case R.id.getUserReturnNull:
                api.getUserReturnNull().enqueue(new BaseCallback<User>(this) {
                    @Override
                    protected void onSuccess(Call<User> call, Response<User> response){
                        Logger.e(response.body().getUsername() + ";;;;" + response.body().getPassword());
                    }
                });
                break;
            case R.id.getUserListNull:
                api.getUserListNull().enqueue(new BaseCallback<List<User>>(this) {
                    @Override
                    protected void onSuccess(Call<List<User>> call, Response<List<User>> response){

                    }
                });
                break;
            case R.id.getUserList:
                api.getUserList().enqueue(new BaseCallback<List<User>>(this) {
                    @Override
                    protected void onSuccess(Call<List<User>> call, Response<List<User>> response) {
                        for (User u : response.body()) {
                            Logger.e(u.getUsername() + ";;;;" + u.getPassword());
                        }
                    }
                });
                break;
        }
    }

    private void canTransfer() {
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
            protected void onSuccess(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
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
