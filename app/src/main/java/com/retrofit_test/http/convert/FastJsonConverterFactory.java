package com.retrofit_test.http.convert;

import com.alibaba.fastjson.JSON;
import com.retrofit_test.util.Logger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by 黄海 on 2017/4/12.
 */
public class FastJsonConverterFactory extends Converter.Factory {
    public static FastJsonConverterFactory create() {
        return new FastJsonConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new FastJsonResponseBodyConverter<>(type);
    }

    static final class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private static final String TAG = "FastJsonResponseBodyConverter";
        Type type;

        public FastJsonResponseBodyConverter(Type type) {
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            Logger.i(TAG, "convert() called with: value = [" + value + "]");
            return JSON.parseObject(value.string(), type);
        }
    }

}
