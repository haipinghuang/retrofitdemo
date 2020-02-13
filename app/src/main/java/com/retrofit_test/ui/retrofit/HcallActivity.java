package com.retrofit_test.ui.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.retrofit_test.R;
import com.retrofit_test.api.HApi;
import com.retrofit_test.bean.BaseResponse;
import com.retrofit_test.bean.User;
import com.retrofit_test.http.BaseCallback;
import com.retrofit_test.http.DialogCallback;
import com.retrofit_test.http.SimpleOkHttpCallBack;
import com.retrofit_test.http.progress.DownloadCallback;
import com.retrofit_test.http.progress.UploadRequestBody;
import com.retrofit_test.ui.okhttp.OkHttpActivity;
import com.retrofit_test.util.ApiUtils;
import com.retrofit_test.util.FileUtils;
import com.retrofit_test.util.Logger;

import java.io.File;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class HcallActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    HApi api;
    TextView tv_content;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hcall);
        api = ApiUtils.getRetrofit().create(HApi.class);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }


    public void click(View v) {
        switch (v.getId()) {
            case R.id.goOkHttp:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.getNullUrl:
                api.getNullUrl("value-header").enqueue(new DialogCallback<String>(this) {
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
            case R.id.getDelay3s:
                getDelay(3);
                break;
            case R.id.getDelay30s:
                getDelay(30);
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
                api.getValidatedUsers(new String[]{"hai", "huang", "li", "chen"}).enqueue(new BaseCallback<BaseResponse<List<User>>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                        tv_content.setText(response.body().toString());
                    }

                });
                break;
            case R.id.postUser:
                api.postUser("hai").enqueue(new BaseCallback<User>(this) {
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
                map.put("zhang", "san");
                api.postUsers(map).enqueue(new BaseCallback<BaseResponse<List<User>>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
                break;
            case R.id.uploadFileWithUsername:
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "android-studio-ide-173.4907809-windows.exe");
                if (file.exists())
                    Logger.i("file name=" + file.getName() + ",length=" + file.length());
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MultipartBody.FORM, file));
                api.uploadFileWithName(filePart, "huang").enqueue(new DialogCallback<BaseResponse<String>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<String>> call, Response<BaseResponse<String>> response) {
                        if (response.body().getCode() == BaseResponse.CODE_FAILED) {
                            tv_content.setText(response.body().getMsg());
                        } else tv_content.setText(response.body().getData());
                    }
                });
                break;
            case R.id.uploadFileReturnString://android-studio-ide-173.4907809-windows.exe   QQ9.0.5.exe
                uploadFile(false);
                break;
            case R.id.uploadProgressFileReturnString://android-studio-ide-173.4907809-windows.exe   QQ9.0.5.exe
                uploadFile(true);
                break;
            case R.id.downloadFile:
                api.downloadFile("genymotion.exe").enqueue(new DialogCallback<ResponseBody>(this) {
                    @Override
                    public void onSuccess(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                FileUtils.response2File(response.body());
                            }
                        }).start();
                    }
                });
//                downloadFile("http://10.200.6.38:8080/retrofitweb/12345q.exe");
                break;
            case R.id.download:
//                api.download("http://10.200.6.38:8080/retrofitweb/app-debug.apk").enqueue(new DialogCallback<ResponseBody>(this) {
//                    @Override
//                    public void onSuccess(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                FileUtils.response2File(response.body());
//                            }
//                        }).start();
//                    }
//                });
                downloadFile("http://10.200.6.38:8080/retrofitweb/12345q.exe");
                break;
        }
    }

    void downloadFile(String uri) {

        api.download(uri).enqueue(new DownloadCallback(this, Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".apk") {
            @Override
            public void onProgress(long totalRead, long contentLength) {
                String percent = NumberFormat.getPercentInstance().format(1.0 * totalRead / contentLength).toString();
                Log.i(TAG, "下载进度 with: totalRead = [" + totalRead + "], contentLength = [" + contentLength + "], progress = [" + percent + "]");
            }
        });
    }

    /**
     * @param progress 是否展示上传进度
     */
    private void uploadFile(boolean progress) {
        MultipartBody.Part filePart;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "QQ9.0.5.exe");
        if (file.exists())
            Logger.i("file name=" + file.getName() + ",length=" + file.length());
        RequestBody body = RequestBody.create(MultipartBody.FORM, file);
        UploadRequestBody progressBody = null;
        if (progress) {
            progressBody = new UploadRequestBody(body, new SimpleOkHttpCallBack() {
                @Override
                public void onProgress(long sended, long total) {
                    String percent = NumberFormat.getPercentInstance().format(1.0 * sended / total).toString();
                    Log.i(TAG, "onProgress() called with: sended = [" + sended + "], total = [" + total + "], Progress = [" + percent + "]");
                }
            });
        }
        filePart = MultipartBody.Part.createFormData("file", file.getName(), progress ? progressBody : body);
        api.uploadFileReturnString(filePart).enqueue(new DialogCallback<String>(this) {

            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                Log.i(TAG, "uploadFileReturnString onSuccess: =" + response.body());
            }
        });
    }


    private void getDelay(int delaySec) {
        api.getDelay(delaySec).enqueue(new DialogCallback<String>(this) {
            @Override
            public void onSuccess(Call<String> call, Response<String> response) {
                tv_content.setText(response.body().toString());
            }
        });
    }


}
