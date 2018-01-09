package com.dev.rxnetmodule.upload;

import io.reactivex.observers.DefaultObserver;

/**
 * Created on 2017/12/23 17:46.
 * 上传文件的RxJava2回调.
 * @author by 王可可
 * @version 1.0
 */
public abstract class FileUploadObserver<T> extends DefaultObserver<T> {

    @Override
    public void onNext(T t) {
        onUploadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onUploadFail(e);
    }

    @Override
    public void onComplete() {

    }

    // 上传成功的回调
    public abstract void onUploadSuccess(T t);

    // 上传失败回调
    public abstract void onUploadFail(Throwable e);

    // 上传进度回调,单文件进度处理
    public abstract void onProgress(int progress);

    // 上传进度回调,多文件进度处理
    public abstract void onMultipleProgress(long bytesWritten, long contentLength);

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onMultipleProgress(bytesWritten,contentLength);
        onProgress((int) (bytesWritten * 100 / contentLength));
    }
}
