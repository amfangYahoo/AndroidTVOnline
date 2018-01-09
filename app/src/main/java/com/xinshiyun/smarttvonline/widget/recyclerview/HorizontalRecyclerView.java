package com.xinshiyun.smarttvonline.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @Description 横向滚动RecyclerView
 */
public class HorizontalRecyclerView extends RecyclerView {

    public HorizontalRecyclerView(Context context) {
        super(context);
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void smoothScrollBy(int dx, int dy) {
        if (dx > 0) {
            super.scrollBy(dx + 100, dy);
        } else if (dx < 0) {
            super.scrollBy(dx - 100, dy);
        } else {
            super.scrollBy(dx, dy);
        }
    }
}
