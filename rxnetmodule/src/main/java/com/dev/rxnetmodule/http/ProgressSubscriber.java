package com.dev.rxnetmodule.http;


import android.content.Context;
import android.util.Log;


import com.dev.rxnetmodule.util.LogUtils;
import com.dev.rxnetmodule.view.SimpleLoadDialog;

import io.reactivex.Observer;

/**
 * 观察者
 * @param <T>
 */
public  abstract class ProgressSubscriber<T> implements ProgressCancelListener, Observer<T> {


    private SimpleLoadDialog dialogHandler;

    public ProgressSubscriber(Context context) {
        dialogHandler = new SimpleLoadDialog(context,this,true);
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (dialogHandler != null) {
            dialogHandler.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();;
            dialogHandler=null;
        }
    }
    @Override
    public void onError(Throwable e) {
//        e.printStackTrace();
        LogUtils.i("net","message = "+e.getMessage());
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
        dismissProgressDialog();
    }


    @Override
    public void onCancelProgress() {
        /*if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }*/
    }
    protected abstract void _onNext(T t);
    protected abstract void _onError(String message);
}
