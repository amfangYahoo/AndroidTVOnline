package com.xinshiyun.smarttvonline.activity.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinshiyun.smarttvonline.R;

/**
 * author: 王可可
 * Created on 2018/1/4.
 * description: 摇摇摆摆又是一年
 */
public class SettingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting,container,false);

        return view;
    }
}
