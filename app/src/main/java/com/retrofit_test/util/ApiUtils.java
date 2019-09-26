package com.retrofit_test.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.retrofit_test.api.HApi;
import com.retrofit_test.http.HCallAdapterFactory;
import com.retrofit_test.http.LoggingInterceptor;
import com.retrofit_test.http.https.MySSLSocket;
import com.retrofit_test.http.https.MyTrustManager;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 黄海 on 2017/2/7.
 */

public class ApiUtils {
    private static final String TAG = "ApiUtils";
    private static final int CONNECTION_TIMEOUT = 5;
    private static final int READ_TIMEOUT = CONNECTION_TIMEOUT;
    //    private static String BASE_URL = "http://10.200.6.38:8080/retrofitweb/";
    private static String BASE_URL = "http://10.0.2.2:8080/retrofitweb/";//模拟器访问电脑配置
    private static Retrofit retrofit;
    private static String cerFileName = "tomcat-test127.cer";

    public static void init(Context context) {
        X509TrustManager safeTrustManager = MyTrustManager.getSafeTrustManager(MyTrustManager.prepareTrustManager(context, cerFileName));

//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
//                .addNetworkInterceptor()
//                .cookieJar(cookieJar)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
//                .sslSocketFactory(MySSLSocket.createSafeSocketFactory(context, cerFileName), safeTrustManager)
                .sslSocketFactory(MySSLSocket.createUnSafeSocketFactory())
//                .hostnameVerifier(new MyHostNameVerifier())
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(new HCallAdapterFactory(new Handler(Looper.getMainLooper())))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())//这个顺序不能换
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .baseUrl(BASE_URL)
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) throw new IllegalStateException("please call init first");
        return retrofit;
    }
}
