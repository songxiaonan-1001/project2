package com.example.projecttwo.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础Fragment
 * MVP框架中BaseFragment类应该具备如下特征：
 * 1.应该包含用来处理网络数据逻辑的P层
 * 2.应该具备界面初始化的方法initView
 * 3.应该具备数据初始化的方法initData
 * 4.获取当前应该显示的xml布局页面
 * 5.生命周期结束的时候解绑p层的关联
 *
 * @param <P>
 */
public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {
    protected P presenter;
    protected Context context;
    protected Activity activity;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        activity = getActivity();
        unbinder = ButterKnife.bind(this, view);
        presenter = createPresenter();
        if (presenter != null) presenter.attachView(this);
        initView();
        initData();
    }

    //获取子类布局id
    protected abstract int getLayout();

    //初始化界面
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //创建P层
    protected abstract P createPresenter();

    @Override
    public void showTips(String msg) {
        Log.i("TAG", "showTips: " + msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁方法中解绑P层对象
        if (presenter != null) presenter.detachView();
        //解绑视图
        if (unbinder != null) unbinder.unbind();
    }
}
