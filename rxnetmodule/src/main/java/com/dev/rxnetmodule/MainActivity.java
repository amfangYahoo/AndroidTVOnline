package com.dev.rxnetmodule;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.rxnetmodule.base.ActivityLifeCycleEvent;
import com.dev.rxnetmodule.base.BaseActivity;
import com.dev.rxnetmodule.enity.Subject;
import com.dev.rxnetmodule.http.Api;
import com.dev.rxnetmodule.http.HttpUtil;
import com.dev.rxnetmodule.http.ProgressSubscriber;
import com.dev.rxnetmodule.util.CacheMode;
import com.dev.rxnetmodule.view.SimpleLoadDialog;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 *
 */
public class MainActivity extends BaseActivity {

    private TextView mText;
    private SimpleLoadDialog dialogHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogHandler = new SimpleLoadDialog(MainActivity.this, null, true);
        mText = (TextView) findViewById(R.id.text);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
                doGet();
            }
        });

    }

    private void doGet() {
        //获取豆瓣电影TOP 100
        Observable ob = Api.getDefault().getTopMovie(0, 30);


        Log.e("net", "开始网络请求");
        mText.setText("");
        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<Subject>>(this) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void _onNext(List<Subject> list) {
                Log.e("net", "list = " + list);
                String str = "";
                for (int i = 0; i < list.size(); i++) {
                    str += "电影名：" + list.get(i).getTitle() + "\n";
                }
                mText.setText(str);
            }
        }, "cacheKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, true, false, CacheMode.NET_FIRST);
    }

}
