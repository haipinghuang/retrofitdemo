package com.retrofit_test.ui.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.LoggingInterceptor;
import com.retrofit_test.util.Logger;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
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
    private static final String TAG = "OkHttpActivity";
    String baseUrl = "http://10.200.6.38:8080/retrofitweb/";//http://10.200.6.38  10.0.2.2
    OkHttpClient okHttpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        setTitle("OkHttpActivity");
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
//        okHttpClient = new OkHttpClient.Builder().build();
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
            case R.id.post18:
                uploadFile("app-debug.apk");
                break;
            case R.id.post70:
                uploadFile("android-5.0.2_r1.7z");//android-5.0.2_r1.7z  ideaIC-2018.2.4.win.zip
                break;
            case R.id.download:
                downloadFile(baseUrl + "12345q.exe", Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".exe");
                break;
            case R.id.downloadFile:
                downloadFile(baseUrl + "downloadFile?fileName=12345q.exe", Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".exe");
                break;
        }
    }

    void downloadFile(String url, String dir, String fileName) {
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new DownloadCallback(dir, fileName) {
            @Override
            public void onProgress(long totalRead, long contentLength) {
                super.onProgress(totalRead, contentLength);
                String format = NumberFormat.getPercentInstance().format(1.0 * totalRead / contentLength);
                Logger.e("downloading progress=" + format);
            }
        });
    }

    void uploadFile(String fileName) {
        RequestBody file = RequestBody.create(MultipartBody.FORM, new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName));
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, file)
                .build();
        Request request = new Request.Builder().post(body).url(baseUrl + "uploadFileWithName")//uploadFile, uploadFileWithName
                .build();
        okHttpClient.newCall(request).enqueue(cb);
    }

    private void json() {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        String jsonString = com.alibaba.fastjson.JSON.toJSONString(new User("huanghai", "123456"));
        RequestBody body = RequestBody.create(JSON, jsonString);

        final Request request = new Request.Builder().addHeader("key-header", "value-header")
                .url(baseUrl)
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(cb);


    }

    private void post() {
        RequestBody formBody = new FormBody.Builder().add("username", "huanghai").build();
        Request request = new Request.Builder().addHeader("key-header", "value-header")
                .url(baseUrl)
                .post(formBody)
                .build();
        okHttpClient.newCall(request).enqueue(cb);
    }

    Callback cb = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e(TAG, "onFailure() called with: call = [" + call + "], e = [" + e + "]");
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(TAG, "onResponse() called with: call = [" + call + "], response = [" + response.body().string() + "]");
        }
    };
}
