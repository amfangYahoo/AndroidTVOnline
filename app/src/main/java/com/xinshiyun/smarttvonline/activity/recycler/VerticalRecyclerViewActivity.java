package com.xinshiyun.smarttvonline.activity.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.adapter.MyRecyclerAdapter;
import com.xinshiyun.smarttvonline.widget.recyclerview.VerticalRecyclerView;

/**
 * @Description 纵向RecyclerView
 */
public class VerticalRecyclerViewActivity extends Activity {
    private VerticalRecyclerView verticalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_recycler);
        verticalRecyclerView = (VerticalRecyclerView) findViewById(R.id.verticalRecyclerView);
        verticalRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        verticalRecyclerView.setAdapter(new MyRecyclerAdapter(this));
    }
}
