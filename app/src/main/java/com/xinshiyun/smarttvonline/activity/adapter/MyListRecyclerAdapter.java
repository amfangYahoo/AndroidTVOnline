package com.xinshiyun.smarttvonline.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.rxnetmodule.enity.DataBean;
import com.xinshiyun.smarttvonline.R;
import com.xinshiyun.smarttvonline.activity.listener.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @date 2017/4/11 15:35
 */
public class MyListRecyclerAdapter extends RecyclerView.Adapter<MyListRecyclerAdapter.ViewHolder> {

    private Context context;

    private List<DataBean> listData;

    public MyListRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DataBean> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_recycler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemTV.setText(listData.get(position).getDesc());
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
        public TextView itemTV;
        public RelativeLayout itemRL;

        public ViewHolder(View itemView) {
            super(itemView);
            itemTV = (TextView) itemView.findViewById(R.id.itemTV);
            itemRL = (RelativeLayout) itemView.findViewById(R.id.itemRL);
        }
    }
}
