package com.example.projecttwo.ui.home.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.common.Constant;
import com.example.projecttwo.models.bean.IndexBean;

import java.util.List;

public class BrandAdapter extends BaseAdapter {

    public BrandAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_brand_item;
    }

    @Override
    public void bingData(BaseViewHolder holder, Object o) {
        IndexBean.DataBean.BrandListBean data = (IndexBean.DataBean.BrandListBean) o;
        ImageView img_brand = (ImageView) holder.getView(R.id.iv_home_brand);
        TextView txt_name = (TextView)holder.getView(R.id.tv_home_brand);
        TextView txt_price = (TextView)holder.getView(R.id.tv_home_price);

        Glide.with(mContext).load(data.getNew_pic_url()).into(img_brand);
        txt_name.setText(data.getName());
        txt_price.setText(Constant.PRICE_MODEL.replace("$",data.getFloor_price()));
    }
}
