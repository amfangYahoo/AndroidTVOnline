package com.xinshiyun.smarttvonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.imageswitch.ImageSwitchActivity;
import com.xinshiyun.smarttvonline.activity.recycler.HorizontalRecyclerViewActivity;
import com.xinshiyun.smarttvonline.activity.recycler.ListRecyclerViewActivity;
import com.xinshiyun.smarttvonline.activity.recycler.VerticalRecyclerViewActivity;
import com.xinshiyun.smarttvonline.activity.video.OnlineVideoActivity;
import com.xinshiyun.smarttvonline.activity.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

    }

    public void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.item1:
                startActivity(new Intent(this, HorizontalRecyclerViewActivity.class));
                break;
            case R.id.item2:
                startActivity(new Intent(this, VerticalRecyclerViewActivity.class));
                break;
            case R.id.item3:
//                Toast.makeText(this, "点击了item3", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.item4:
                startActivity(new Intent(this, OnlineVideoActivity.class));
                break;
            case R.id.item5:
                startActivity(new Intent(this, ListRecyclerViewActivity.class));
//                Toast.makeText(this, "点击了item5", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item6:
//                Toast.makeText(this, "点击了item6", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ImageSwitchActivity.class));
                break;
            case R.id.item7:
                Toast.makeText(this, "点击了item7", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item8:
                Toast.makeText(this, "点击了item8", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
