package com.dev.rxnetmodule.http;


import com.dev.rxnetmodule.base.ActivityLifeCycleEvent;
import com.dev.rxnetmodule.enity.HttpResult;
import com.dev.rxnetmodule.upload.FileUploadObserver;
import com.dev.rxnetmodule.upload.MultipartBuilder;
import com.dev.rxnetmodule.upload.UploadFileRequestBody;
import com.dev.rxnetmodule.util.CacheMode;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.ResponseBody;

/**
 * forceRefresh一般建议设置为false，可通过设置缓存时间长短来控制网络请求频率，
 * <p>
 * 比如：默认可使用缓存，即CacheMode.CACHE_FIRST,当用户下拉刷新的时候可以设置forceRefresh为true，强制请求网络数据
 */
public class HttpUtil {

    /**
     * 数据缓存有效期
     */
    public static final long CACHE_VALID_TIME = 60 * 1000 * 10;

    /**
     * 构造方法私有
     */
    private HttpUtil() {
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    /**
     * 获取单例
     */
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 添加线程管理并订阅，理清楚里路后，可使用本方法个性化自定义设置，否则请使用下方重载方法
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     * @param isSave           是否缓存
     * @param forceRefresh     是否强制刷新
     * @param cacheMode        缓存模式
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh, CacheMode cacheMode) {


        //数据预处理
        ObservableTransformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh, cacheMode).subscribeWith(subscriber);
    }

    /**
     * 默认强制使用网络，不使用缓存
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay,请务必确保key唯一性
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        //数据预处理
        ObservableTransformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, false, true, CacheMode.NO_CACHE).subscribeWith(subscriber);
    }

    /**
     * isSave默认为true（一旦请求网络就会更新缓存）,forceRefresh默认为false
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     * @param cacheMode        缓存模式
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, CacheMode cacheMode) {
        //数据预处理
        ObservableTransformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, true, false, cacheMode).subscribeWith(subscriber);
    }

    /**
     * 可控制是否强制使用网络
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean forceRefresh, CacheMode cacheMode) {

        //数据预处理
        ObservableTransformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        if (forceRefresh) {
            RetrofitCache.load(cacheKey, observable, false, forceRefresh, cacheMode).subscribeWith(subscriber);
        } else {
            RetrofitCache.load(cacheKey, observable, true, forceRefresh, cacheMode).subscribeWith(subscriber);
        }
    }


    /*
     * 文件上传
     */

    /**
     * 单上传文件的封装.
     *
     * @param url                完整的接口地址
     * @param file               需要上传的文件
     * @param fileUploadObserver 上传回调
     */
    public void upLoadFile(String url, File file,
                           FileUploadObserver<ResponseBody> fileUploadObserver) {

        UploadFileRequestBody uploadFileRequestBody =
                new UploadFileRequestBody(file, fileUploadObserver);

        Api.getDefault()
                .uploadFile(url,MultipartBuilder.fileToMultipartBody(file,
                        uploadFileRequestBody))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver);

    }

    /**
     * 多文件上传.
     *
     * @param url                上传接口地址
     * @param files              文件列表
     * @param fileUploadObserver 文件上传回调
     */
    public void upLoadFiles(String url, List<File> files,
                            FileUploadObserver<ResponseBody> fileUploadObserver) {

        Api.getDefault()
                .uploadFile(url,MultipartBuilder.filesToMultipartBody(files,
                        fileUploadObserver))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver);

    }


}
