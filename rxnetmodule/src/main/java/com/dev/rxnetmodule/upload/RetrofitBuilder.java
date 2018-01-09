package com.dev.rxnetmodule.upload;

import com.dev.rxnetmodule.http.Url;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 2017/12/23 17:58.
 * Retrofit构造器
 * @author by 王可可
 * @version 1.0
 */

public class RetrofitBuilder {

    private static Retrofit retrofit;

    public static synchronized Retrofit buildRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().client(OkHttpManager.getInstance())
//                    .baseUrl(Url.BASE_UPLOAD_FILE_URL)
                    .baseUrl(Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
