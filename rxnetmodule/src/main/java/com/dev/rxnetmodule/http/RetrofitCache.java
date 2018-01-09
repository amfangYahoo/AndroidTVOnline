package com.dev.rxnetmodule.http;

import com.dev.rxnetmodule.util.CacheMode;
import com.dev.rxnetmodule.util.LogUtils;
import com.orhanobut.hawk.Hawk;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * 加载缓存时是不显示loadingDialog的
 */
public class RetrofitCache {
    /**
     * @param cacheKey     缓存的Key
     * @param fromNetwork
     * @param isSave       是否缓存
     * @param forceRefresh 是否强制刷新
     * @param cacheMode    缓存模式
     * @param <T>
     * @return
     */
    public static <T> Observable<T> load(final String cacheKey,
                                         Observable<T> fromNetwork,
                                         boolean isSave, boolean forceRefresh, CacheMode cacheMode) {

        switch (cacheMode) {
            case NO_CACHE:
                return noCacheDeal(cacheKey, fromNetwork, isSave, forceRefresh);
            case CACHE_FIRST:
                return cacheFirstDeal(cacheKey, fromNetwork, isSave, forceRefresh);
            case NET_FIRST:
                return netFirstDeal(cacheKey, fromNetwork, isSave, forceRefresh);
            default:
                return noCacheDeal(cacheKey, fromNetwork, isSave, forceRefresh);
        }

    }

    /**
     * @param cacheKey
     * @param fromNetwork
     * @param isSave
     * @param forceRefresh
     * @param <T>
     * 网络优先，有缓存先展示缓存，更优雅，默认缓存长期有效
     */
    private static <T> Observable<T> netFirstDeal(final String cacheKey, Observable<T> fromNetwork, boolean isSave, boolean forceRefresh) {
        LogUtils.i("net","------- > netFirstDeal");
        T cache = Hawk.get(cacheKey);
        //获取缓存observable
        Observable<T> fromCache = null;
        if (null != cache) {
            fromCache = getCacheObservable(cache,true,cacheKey);
        }else {
            fromCache = getCacheObservable(cache,false,cacheKey);
        }

        //是否缓存
        if (isSave) {
            /**
             *这里的fromNetwork 不需要指定Schedule, 在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Function<T, T>() {
                @Override
                public T apply(T result) throws Exception {
                    boolean isSuccess = Hawk.put(cacheKey, result);
                    LogUtils.i("net", "Hawk.put == > " + isSuccess);
                    return result;
                }
            });
        }

        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {
            LogUtils.i("net", "fromCache == > " + fromCache+"---- > fromNetwork = "+fromNetwork);
            return Observable.concat(fromCache, fromNetwork);
        }
    }

    /**
     * @param cacheKey
     * @param fromNetwork
     * @param isSave
     * @param forceRefresh
     * @param <T>
     * 使用缓存，缓存优先，缓存过期或者无缓存使用网络
     */
    private static <T> Observable<T> cacheFirstDeal(final String cacheKey, Observable<T> fromNetwork, boolean isSave, boolean forceRefresh) {

        LogUtils.i("net","------- > cacheFirstDeal");
        Observable<T> fromCache = null;
        T cache = Hawk.get(cacheKey);
        Long lastTime = Hawk.get(cacheKey+"_time");
        //lastTime为null说明暂无该key的缓存
        if(null != lastTime){
            long currentTime = System.currentTimeMillis();
            long hasTime = currentTime - lastTime;
            //获取缓存observable
            LogUtils.i("net","hasTime = "+hasTime+"----cache = "+cache);
            if (null != cache && hasTime <= HttpUtil.CACHE_VALID_TIME ) {
                fromCache = getCacheObservable(cache,true,cacheKey);
            }else{
                fromCache = getCacheObservable(cache,false,cacheKey);
            }
        }else {
            fromCache = getCacheObservable(cache,false,cacheKey);
        }

        //是否缓存,true，每次请求网络都会缓存下最新的数据
        if (isSave) {
            /**
            *这里的fromNetwork 不需要指定Schedule, 在handleRequest中已经变换了
            */
            fromNetwork = fromNetwork.map(new Function<T, T>() {
                @Override
                public T apply(T result) throws Exception {
                    Hawk.put(cacheKey, result);
                    long curTime = System.currentTimeMillis();
                    Hawk.put(cacheKey+"_time", curTime);
                    return result;
                }
            });
        }

        //强制刷新
        if (forceRefresh) {
            return fromNetwork;
        } else {

            return Observable.concat(fromCache, fromNetwork).filter(new Predicate<T>() {
                @Override
                public boolean test(T t) throws Exception {
                    return t != null;
                }
            }).take(1);


            /*return Observable.concat(fromCache, fromNetwork).takeFirst(new Func1<T, Boolean>() {
                @Override
                public Boolean call(T t) {
                    LogUtils.i("net", "t == " + t);
                    return t != null;
                }
            });*/
        }
    }

    /**
     * @param cacheKey
     * @param fromNetwork
     * @param isSave
     * @param forceRefresh
     * @param <T>
     */
    private static <T> Observable<T> noCacheDeal(String cacheKey, Observable<T> fromNetwork, boolean isSave, boolean forceRefresh) {
        LogUtils.i("net","------- > noCacheDeal");
        return fromNetwork;
    }

    private static <T> Observable<T> getCacheObservable(final T cache, final boolean hasCache, final String cacheKey) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                if (hasCache) {
                    e.onNext(cache);
                    e.onComplete();
                } else {
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

}
