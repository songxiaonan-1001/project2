package com.example.projecttwo.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.models.bean.IndexBean;

import java.util.List;

/**
 * 新品首发适配器
 */
public class NewGoodsListAdapter extends BaseAdapter {
    public NewGoodsListAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home_newgoods_item;
    }

    @Override
    public void bingData(BaseViewHolder holder, Object o) {
        IndexBean.DataBean.NewGoodsListBean data = (IndexBean.DataBean.NewGoodsListBean) o;
        ImageView img_newgoods = (ImageView) holder.getView(R.id.iv_home_newgoods);
        TextView txt_name = (TextView) holder.getView(R.id.tv_home_newgoods_name);
        TextView txt_price = (TextView) holder.getView(R.id.tv_home_newgoods_price);

        Glide.with(mContext).load(data.getList_pic_url()).into(img_newgoods);
        txt_name.setText(data.getName());
        txt_price.setText(data.getRetail_price());
    }
}
