package com.xinshiyun.smarttvonline.activity.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.dev.rxnetmodule.base.ActivityLifeCycleEvent;
import com.dev.rxnetmodule.base.BaseActivity;
import com.dev.rxnetmodule.enity.DataBean;
import com.dev.rxnetmodule.http.Api;
import com.dev.rxnetmodule.http.HttpUtil;
import com.dev.rxnetmodule.http.ProgressSubscriber;
import com.dev.rxnetmodule.util.CacheMode;
import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.adapter.MyListRecyclerAdapter;
import com.xinshiyun.smarttvonline.activity.listener.OnRecyclerItemClickListener;
import com.xinshiyun.smarttvonline.activity.webview.WebViewActivity;
import com.xinshiyun.smarttvonline.widget.recyclerview.VerticalRecyclerView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


/**
 * @Description listRecyclerView
 */
public class ListRecyclerViewActivity extends BaseActivity implements OnRecyclerItemClickListener {
    private VerticalRecyclerView verticalRecyclerView;
    private MyListRecyclerAdapter adapter;

    private List<DataBean> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_recycler);
        verticalRecyclerView = (VerticalRecyclerView) findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyListRecyclerAdapter(this);

        verticalRecyclerView.setAdapter(adapter);


        getGankIoData();
    }


    private void getGankIoData() {
        Observable ob = Api.getDefault().getGankData();

        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<DataBean>>(this) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(ListRecyclerViewActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void _onNext(List<DataBean> data) {
                dataList = data;
                Log.e("data","size = "+data.size());
                adapter.setData(data);
            }
        }, "getGankData", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false, CacheMode.NO_CACHE);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ListRecyclerViewActivity.this, WebViewActivity.class);
        intent.putExtra("url",dataList.get(position).getUrl());
        startActivity(intent);
    }
}
