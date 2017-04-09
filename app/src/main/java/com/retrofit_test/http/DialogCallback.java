package com.retrofit_test.http;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by 黄海 on 2017/4/7.
 */

public abstract class DialogCallback<T> extends BaseCallBack<T> {
    private ProgressDialog progressDialog;

    public DialogCallback(Context context) {
        super(context);
    }

    @Override
    public void onStart() {
        super.onStart();
        initProgressDialog(context);
    }

    private void initProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
