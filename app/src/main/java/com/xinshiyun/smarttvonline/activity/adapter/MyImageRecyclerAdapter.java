package com.xinshiyun.smarttvonline.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dev.rxnetmodule.enity.DataBean;
import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.listener.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @date 2017/4/11 15:35
 */
public class MyImageRecyclerAdapter extends RecyclerView.Adapter<MyImageRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<DataBean> listData;

    public MyImageRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DataBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context)
                .load(listData.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.loading)
                .into(holder.imageView);
        holder.itemRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnRecyclerItemClickListener)context).onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == listData ? 0 : listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public RelativeLayout itemRL;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            itemRL = (RelativeLayout) itemView.findViewById(R.id.itemRL);
        }
    }
}
