package com.retrofit_test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.retrofit_test.R;
import com.retrofit_test.api.TjApi;
import com.retrofit_test.bean.BaseCallback;
import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.Mixed;
import com.retrofit_test.bean.User;
import com.retrofit_test.util.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TjActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    TjApi api;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj);
        api = ApiUtils.getRetrofit().create(TjApi.class);
        et = (EditText) findViewById(R.id.et);
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.getString:
                api.getString(et.getText().toString()).enqueue(new BaseCallback<BaseResponse<String>>(this) {
                    @Override
                    protected void onSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {

                    }
                });
                break;
            case R.id.getMixedNull:
               api.getMixedNull(et.getText().toString()).enqueue(new BaseCallback<BaseResponse<Mixed>>(this) {
                   @Override
                   protected void onSuccess(Call<BaseResponse<Mixed>> call, Response<BaseResponse<Mixed>> response) {

                   }
               });
                break;
            case R.id.getUser:
                api.getUser(et.getText().toString()).enqueue(new BaseCallback<BaseResponse<User>>(this) {
                    @Override
                    protected void onSuccess(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        System.out.println(response.body().getData().getUsername());
                    }
                });
                break;
            case R.id.getUserList:
                api.getUserList(et.getText().toString()).enqueue(new BaseCallback<BaseResponse<List<User>>>(this) {
                    @Override
                    protected void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {

                    }
                });
                break;
            case R.id.getException:
                break;
        }
    }


}
