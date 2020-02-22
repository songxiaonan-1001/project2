package com.example.projecttwo.ui.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.models.bean.IndexBean;

import java.util.List;

public class HotGoodsAdapter extends BaseAdapter {
    public HotGoodsAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_hotgoods_item;
    }

    @Override
    public void bingData(BaseViewHolder holder, Object o) {
        IndexBean.DataBean.HotGoodsListBean data = (IndexBean.DataBean.HotGoodsListBean) o;
        ImageView img_hotgoods = (ImageView) holder.getView(R.id.iv_home_hotgoods);
        TextView txt_name = (TextView) holder.getView(R.id.tv_home_hotgoods_name);
        TextView txt_desc = (TextView) holder.getView(R.id.tv_home_hotgoods_desc);
        TextView txt_price = (TextView) holder.getView(R.id.tv_home_hotgoods_price);

        Glide.with(mContext).load(data.getList_pic_url()).into(img_hotgoods);
        txt_name.setText(data.getName());
        txt_desc.setText(data.getGoods_brief());
        txt_price.setText("¥"+data.getRetail_price());
    }
}
