package com.xinshiyun.smarttvonline.activity.viewpager.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.widget.focus.MetroView;

/**
 * author: 王可可
 * Created on 2018/1/4.
 * description: 摇摇摆摆又是一年
 */
public class HomeFragment extends Fragment implements View.OnFocusChangeListener{

    private MetroView metroViewRight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        metroViewRight = (MetroView) view.findViewById(R.id.item_right);
        metroViewRight.setOnFocusChangeListener(this);
        metroViewRight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
                    Toast.makeText(getActivity(), "点击了最右侧的右键", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId())
        {
            case R.id.item_right:

                break;
        }
    }


}
