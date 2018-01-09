package com.xinshiyun.smarttvonline.activity.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;

import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.adapter.MyRecyclerAdapter;
import com.xinshiyun.smarttvonline.widget.recyclerview.HorizontalRecyclerView;


/**
 * @Description 横向RecyclerView
 */
public class HorizontalRecyclerViewActivity extends Activity {
    private HorizontalRecyclerView horizontalRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_recycler);
        horizontalRecyclerView = (HorizontalRecyclerView) findViewById(R.id.horizontalRecyclerView);
        horizontalRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.HORIZONTAL));
        horizontalRecyclerView.setAdapter(new MyRecyclerAdapter(this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
