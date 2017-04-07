package com.retrofit_test.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.api.TjApi;

public class RxActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    TjApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj);

    }

    void test() {

    }

    public void click(View v) {
        switch (v.getId()) {

        }
    }


}
