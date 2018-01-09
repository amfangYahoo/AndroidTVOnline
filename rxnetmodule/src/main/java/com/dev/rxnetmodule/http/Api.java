package com.dev.rxnetmodule.http;


import android.text.TextUtils;
import android.util.Log;

import com.dev.rxnetmodule.BuildConfig;
import com.dev.rxnetmodule.upload.OkHttpManager;
import com.dev.rxnetmodule.upload.RetrofitBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Api类封装
 */
public class Api {
    private static ApiService SERVICE;

    private static Api mInstance;

    private static Retrofit retrofit;

    public Api() {
        retrofit = RetrofitBuilder.buildRetrofit();
    }

    /**
     * 获取Api实例.
     *
     * @return 返回Api单例
     */
    public static synchronized Api getInstance() {
        if (mInstance == null) {
            mInstance = new Api();
        }
        return mInstance;
    }

    public  <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public static ApiService getDefault() {
        if (SERVICE == null) {
            SERVICE = getInstance().create(ApiService.class);
        }
        return SERVICE;
    }

}
