package com.dev.rxnetmodule.upload;

import android.text.TextUtils;
import android.util.Log;

import com.dev.rxnetmodule.BuildConfig;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created on 2017/12/23 18:00.
 *
 * @author by 王可可
 * @version 1.0
 */

public class OkHttpManager {

    private static OkHttpClient okHttpClient;

    /**
     * 获取OkHttp单例，线程安全.
     *
     * @return 返回OkHttpClient单例
     */
    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    if (BuildConfig.DEBUG) {
                        // 拦截okHttp的日志，如果开启了会导致上传回调被调用两次
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(interceptor);
                    }

                    // 超时时间
                    builder.connectTimeout(30, TimeUnit.SECONDS);// 15S连接超时
                    builder.readTimeout(20, TimeUnit.SECONDS);// 20s读取超时
                    builder.writeTimeout(20, TimeUnit.SECONDS);// 20s写入超时
                    // 错误重连
                    builder.retryOnConnectionFailure(true);

                    /**
                     *  拦截器
                     */
                    builder.addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();

//                    Request.Builder requestBuilder = request.newBuilder();
//                    RequestBody formBody = new FormBody.Builder()
//                            .add("1","2")
//                            .build();

                            HttpUrl.Builder authorizedUrlBuilder = request.url()
                                    .newBuilder();
                            //添加统一参数 如手机唯一标识符,token等
//                            .addQueryParameter("key1","value1")
//                            .addQueryParameter("key2", "value2");

                            String token = Hawk.get("token","");

                            Request newRequest = request.newBuilder()
                                    //对所有请求添加请求头
                                    .header("mobileFlag", "test").addHeader("t", token)
                                    .method(request.method(), request.body())
                                    .url(authorizedUrlBuilder.build())
                                    .build();

                            return  chain.proceed(newRequest);
                        }
                    });

                    /**
                     * 此处模拟，登录返回token，访问其他接口需带上token的一种情况，可根据实际情况定义
                     */
                    builder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Response response = chain.proceed(chain.request());
                            Log.e("net","t = "+response.header("t"));
                            if(!TextUtils.isEmpty(response.header("t"))){
                                Hawk.put("token",response.header("t"));
                            }
                            return response;
                        }
                    });

                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }
}
