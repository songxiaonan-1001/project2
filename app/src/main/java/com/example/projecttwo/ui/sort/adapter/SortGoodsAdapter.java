package com.example.projecttwo.ui.sort.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;

import java.util.List;

public class SortGoodsAdapter extends BaseAdapter {
    public SortGoodsAdapter(List mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected int getLayout() {
        return R.layout.catalog_list_item;
    }

    @Override
    public void bingData(BaseViewHolder holder, Object o) {
        SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean = (SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean) o;
        ImageView img = (ImageView) holder.getView(R.id.img_icon);
        Glide.with(mContext).load(bean.getWap_banner_url()).into(img);
        TextView txtName = (TextView) holder.getView(R.id.txt_name);
        txtName.setText(bean.getName());
    }
}
