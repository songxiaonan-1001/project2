package com.example.projecttwo.ui.home.adapter;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.models.bean.IndexBean;

import java.util.List;

public class TopicListAdapter extends BaseAdapter {
    public TopicListAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_topic_item;
    }

    @Override
    public void bingData(BaseViewHolder holder, Object o) {
        IndexBean.DataBean.TopicListBean data = (IndexBean.DataBean.TopicListBean) o;
        ImageView img_topic = (ImageView) holder.getView(R.id.iv_home_topic);
        TextView txt_name = (TextView) holder.getView(R.id.tv_home_topic_name);
        TextView txt_desc = (TextView) holder.getView(R.id.tv_home_topic_desc);
        TextView txt_price = (TextView) holder.getView(R.id.tv_home_topic_price);

        Glide.with(mContext).load(data.getItem_pic_url()).into(img_topic);
        txt_name.setText(data.getTitle());
        txt_desc.setText(data.getSubtitle());
        txt_price.setText("¥" + data.getPrice_info() + "元起");
    }
}
