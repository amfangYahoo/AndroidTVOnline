package com.xinshiyun.smarttvonline.activity.imageswitch;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.xinshiyun.smarttvonline.activity.adapter.MyImageRecyclerAdapter;
import com.xinshiyun.smarttvonline.activity.listener.OnRecyclerItemClickListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ImageSwitchActivity extends BaseActivity implements OnRecyclerItemClickListener {

    private RecyclerView recyclerView;

    List<DataBean> dataBeanList;
    private MyImageRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switch);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new MyImageRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        getGankImageData();


    }


    private void getGankImageData() {
        Observable ob = Api.getDefault().getGankImageData();

        HttpUtil.getInstance().toSubscribe(ob, new ProgressSubscriber<List<DataBean>>(this) {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(ImageSwitchActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void _onNext(List<DataBean> data) {
                Log.e("data","size = "+data.size());
                dataBeanList = data;
                adapter.setData(data);
            }
        }, "getGankData", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false, CacheMode.NO_CACHE);
    }


    @Override
    public void onItemClick(int position) {

    }
}
