package com.example.projecttwo.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * adapter适配器的基类
 *
 * @param <T> 数据的类型
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    //成员变量
    protected ItemClickHandler itemClickHandler;//接口的成员变量
    protected List<T> mDatas;
    protected Context mContext;

    //构造方法
    public BaseAdapter(List<T> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayout(), parent, false);
        final BaseViewHolder holder = new BaseViewHolder(view);
        //条目点击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickHandler != null) {
                    //点击跳转详情页
                    itemClickHandler.itemClick(holder.getLayoutPosition(), holder);
                }
            }
        });
        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        T t = mDatas.get(position);
        bingData((BaseViewHolder) holder, t);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    //刷新所有列表数据
    public void updata(List<T> list) {
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    //分页加载数据的刷新
    public void refreshList(List<T> list) {
        mDatas.addAll(list);
        notifyDataSetChanged();
    }


    //获取布局的方法(abstract)
    protected abstract int getLayout();

    //绑定数据的方法(abstract)
    public abstract void bingData(BaseViewHolder holder, T t);


    //创建基类的ViewHolder
    public static class BaseViewHolder extends RecyclerView.ViewHolder {
        private SparseArray items;

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
            items = new SparseArray();
        }

        //获取item中的元素?
        public View getView(int id) {
            View view = (View) items.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                items.append(id, view);
            }
            return view;
        }
    }


    //定义一个回调接口
    public interface ItemClickHandler {
        void itemClick(int position, BaseViewHolder holder);
    }

    //设置回调接口的监听(即设置set()方法)
    public void setItemClickHandler(ItemClickHandler itemClickHandler) {
        this.itemClickHandler = itemClickHandler;
    }
}
