package com.retrofit_test.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.retrofit_test.api.RetrofitApi;
import com.retrofit_test.http.HaiCallAdapterFactory;
import com.retrofit_test.http.LoggingInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 黄海 on 2017/2/7.
 */

public class ApiUtils {
    private static final int CONNECTION_TIMEOUT = 5;
    private static final int READ_TIMEOUT = 5;
    private static String BASE_URL = "http://192.168.1.104:8080/webtest/";
    //    private static String BASE_URL = "https://mportal.tianjihuifu.com/";
    private static Retrofit retrofit;
    private static Map<String, Object> map_Api = new HashMap<>();

    public static void init(Context context) {
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .cookieJar(cookieJar)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(new HaiCallAdapterFactory(new Handler(Looper.getMainLooper())))
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).client(builder.build())
                .baseUrl(RetrofitApi.baseUrl)
                .build();
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) throw new IllegalStateException("please call init first");
        return retrofit;
    }

    public static <T> T createApi(Class<T> service) {
        Object api = map_Api.get(service.getName());
        synchronized (map_Api) {
            if (api == null) {
                api = getRetrofit().create(service);
            }
        }
        return (T) api;
    }
}
