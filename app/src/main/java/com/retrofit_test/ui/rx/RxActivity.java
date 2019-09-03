package com.retrofit_test.ui.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.retrofit_test.R;
import com.retrofit_test.api.RxApi;
import com.retrofit_test.util.ApiUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import rx.Subscriber;

public class RxActivity extends AppCompatActivity {
    private static final String TAG = "hhp";
    RxApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tj);
        api = ApiUtils.getRetrofit().create(RxApi.class);
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.getString:
                api.getAString()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(String s) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
        }
    }


}
