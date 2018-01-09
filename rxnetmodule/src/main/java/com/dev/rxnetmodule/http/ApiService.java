package com.dev.rxnetmodule.http;


import com.dev.rxnetmodule.enity.DataBean;
import com.dev.rxnetmodule.enity.HttpResult;
import com.dev.rxnetmodule.enity.Subject;
import com.dev.rxnetmodule.enity.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * apiservice
 */
public interface ApiService {
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("Android/100/1")
    Observable<HttpResult<List<DataBean>>> getGankData();

    @GET("福利/50/1")
    Observable<HttpResult<List<DataBean>>> getGankImageData();

    @FormUrlEncoded
    @POST("api/")
    Observable<HttpResult<User>> loginRequest(@Field("i") String className,
                                              @Field("m") String methodName,
                                              @Field("p") String params
    );









    @POST
    Observable<ResponseBody> uploadFile(@retrofit2.http.Url String url, @Body MultipartBody body);

}
