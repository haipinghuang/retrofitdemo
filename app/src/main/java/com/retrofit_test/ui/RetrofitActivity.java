package com.retrofit_test.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.retrofit_test.R;
import com.retrofit_test.api.RetrofitApi;
import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.BaseCallBack;
import com.retrofit_test.http.DialogCallback;
import com.retrofit_test.util.ApiUtils;
import com.retrofit_test.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    RetrofitApi api;
    TextView tv_content;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        tv_content = (TextView) findViewById(R.id.tv_content);
        api = ApiUtils.getRetrofit().create(RetrofitApi.class);
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.getNullUrl:
                api.getNullUrl().enqueue(new BaseCallBack<String>(this) {
                    @Override
                    public void onSuccess(Call<String> call, Response<String> response) {

                    }
                });
                break;
            case R.id.getUser:
                api.getUser().enqueue(new DialogCallback<User>(this) {
                    @Override
                    public void onSuccess(Call<User> call, Response<User> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.getValidatedUser:
                api.getValidatedUser("li", "sihhh").enqueue(new DialogCallback<BaseResponse<User>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.getValidatedUsers:
                api.getValidatedUsers(new String[]{"hai", "huang", "li", "chen"}).enqueue(new BaseCallBack<BaseResponse<List<User>>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                        tv_content.setText(response.body().toString());
                    }

                });
                break;
            case R.id.postUser:
                api.postUser("hai").enqueue(new BaseCallBack<User>(this) {
                    @Override
                    public void onSuccess(Call<User> call, Response<User> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.postUserName:
                api.postUserName("huang").enqueue(new DialogCallback<User>(this) {
                    @Override
                    public void onSuccess(Call<User> call, Response<User> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.postUsers:
                Map<String, String> map = new HashMap<>();
                map.put("haung", "hai");
                map.put("li", "si");
                map.put("zhang", "san");
                api.postUsers(map).enqueue(new BaseCallBack<BaseResponse<List<User>>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.uploadFileWithUsername:
                final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "app-debug.apk");
                if (file.exists())
                    Logger.i("file name=" + file.getName() + ",length=" + file.length());
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));
                api.uploadFileWithName(filePart, "huang").enqueue(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Call<String> call, Response<String> response) {
                        tv_content.setText("上传成功");
                    }
                });
                break;
            case R.id.downloadFile:
                api.downloadFile("app-debug.apk").enqueue(new DialogCallback<ResponseBody>(this) {
                    @Override
                    public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {
                    }

                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        if (response.code() == 200 || response.code() == 201) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    InputStream inputStream = response.body().byteStream();
                                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".apk");
                                    byte[] buffer = new byte[2048];
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(file);
                                        while ((inputStream.read(buffer)) != -1) {
                                            fos.write(buffer);
                                        }
                                        fos.flush();
                                        Log.e("hhp", file.getName() + "下载成功=" + file.length());

                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            inputStream.close();
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();
                        }
                    }
                });
                break;

        }

    }
}
