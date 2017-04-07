package com.retrofit_test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.retrofit_test.R;
import com.retrofit_test.api.TjApi;
import com.retrofit_test.http.DialogCallback;
import com.retrofit_test.util.ApiUtils;

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
                api.getNullUrl().enqueue(new DialogCallback<String>(this) {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }
                });
                break;
            case R.id.getMixedNull:

                break;
            case R.id.getUser:

                break;
            case R.id.getUserList:

                break;
            case R.id.getException:
                break;
        }
    }


}
