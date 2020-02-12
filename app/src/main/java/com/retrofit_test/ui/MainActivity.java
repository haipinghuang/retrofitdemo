package com.retrofit_test.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.ui.okhttp.OkHttpActivity;
import com.retrofit_test.ui.retrofit.CallActivity;
import com.retrofit_test.ui.retrofit.HcallActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.okhttp:
                startActivity(new Intent(this, OkHttpActivity.class));
                break;
            case R.id.retrofit:
                startActivity(new Intent(this, CallActivity.class));
                break;
            case R.id.Hcall:
                startActivity(new Intent(this, HcallActivity.class));
                break;
            case R.id.retrofitRx:
                break;
        }
    }
}
