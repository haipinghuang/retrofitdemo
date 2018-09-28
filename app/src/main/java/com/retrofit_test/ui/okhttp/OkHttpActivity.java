package com.retrofit_test.ui.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.retrofit_test.R;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 直接测试okHttp
 * Created by huanghp on 2018/9/28.
 * Email h1132760021@sina.com
 */
public class OkHttpActivity extends AppCompatActivity {
    String baseUrl = "http://10.200.6.38:8080/retrofitweb/";
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.get:
                break;
            case R.id.post:
                post();
                break;
            case R.id.json:
                break;
        }
    }

    private void post() {
        RequestBody formBody = new FormBody.Builder().add("username", "huanghai").build();
        Request request = new Request.Builder().addHeader("key-header", "value-header")
                .url(baseUrl)
                .post(formBody)
                .build();
    }
}
