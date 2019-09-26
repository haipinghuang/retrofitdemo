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
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableSampleWithObservable;
import io.reactivex.observers.SafeObserver;
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
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {

                            }
                        });
                break;

        }
    }


}
