package com.retrofit_test.ui.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.LoggingInterceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
        setTitle("OkHttpActivity");
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.get:
                break;
            case R.id.post:
                post();
                break;
            case R.id.json:
                json();
                break;
            case R.id.file:
                break;
        }
    }


    private void json() {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = com.alibaba.fastjson.JSON.toJSONString(new User("huanghai", "123456"));
                RequestBody body = RequestBody.create(JSON, jsonString);
                Request request = new Request.Builder().addHeader("key-header", "value-header")
                        .url(baseUrl)
                        .post(body)
                        .build();
                try {
                    Response execute = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void post() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody formBody = new FormBody.Builder().add("username", "huanghai").build();
                Request request = new Request.Builder().addHeader("key-header", "value-header")
                        .url(baseUrl)
                        .post(formBody)
                        .build();
                try {
                    Response execute = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
