package com.dev.rxnetmodule.http;

import com.dev.rxnetmodule.base.ActivityLifeCycleEvent;
import com.dev.rxnetmodule.enity.HttpResult;
import com.dev.rxnetmodule.util.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxHelper {

    /**
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> tObservable) {


                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
                            @Override
                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) throws Exception {
                                LogUtils.e("net","-----> activityLifeCycleEvent-----> "+activityLifeCycleEvent.equals(event));
                                return activityLifeCycleEvent.equals(event);
                            }
                        }).take(1);





                return (ObservableSource<T>) tObservable.flatMap(new Function<HttpResult<T>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(HttpResult<T> result) throws Exception {
                        if (!result.isError()) {
                            return createData(result.getResults());
                        } else {
                            LogUtils.i("net","result = "+result);
                            return Observable.error(new ApiException("请求失败"));
                        }
                    }
                }).takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());


            }
        };
    }



    /**
     * @param <T>
     * @return
     */
    /*public static <T> Observable.Transformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        return new Observable.Transformer<HttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResult<T>> tObservable) {


                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
                        lifecycleSubject.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                            @Override
                            public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                                return activityLifeCycleEvent.equals(event);
                            }
                        });

                return tObservable.flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResult<T> result) {
                        if (result.getCount() != 0) {
                            return createData(result.getSubjects());
                        } else {
                            LogUtils.i("net","result = "+result);
                            return Observable.error(new ApiException(result.getCount()));
                        }
                    }
                }).takeUntil(compareLifecycleObservable).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }*/



    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> obe) throws Exception {
                try {
                    obe.onNext(data);
                    obe.onComplete();
                } catch (Exception e) {
                    obe.onError(e);
                }
            }
        });



    }

}
