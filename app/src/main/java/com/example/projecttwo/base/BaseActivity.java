package com.example.projecttwo.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 定义Activity的基类，用来实现mvp中V层的基础功能
 * MVP框架中BaseActivity类应该具备如下特征：
 * 1.应该包含用来处理网络数据逻辑的P层
 * 2.应该具备界面初始化的方法initView
 * 3.应该具备数据初始化的方法initData
 * 4.获取当前应该显示的xml布局页面
 * 5.生命周期结束的时候解绑p层的关联
 * 泛型P:继承了IBasePresenter 接口的一个接口(by:接口可以被接口继承，但不允许被类继承)
 *
 * @param <P>
 */
public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected P presenter;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initView();//初始化界面的操作
        presenter = createPresenter();//创建p的方法
        if (presenter != null) {
            presenter.attachView(this);
        }
        initData();//初始化数据
    }

    /**
     * 通过模板的设计模式,定义需要处理的方法
     */
    protected abstract int getLayout();

    //初始化界面的操作
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //创建p的方法
    protected abstract P createPresenter();

    /**
     * 用来显示提示信息
     *
     * @param msg
     */
    @Override
    public void showTips(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * activity移除的时候解绑presenter和V层
     * 解绑当前activity的butterKnife
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            //解绑与P层的关联
            presenter.detachView();
        }
        if (unbinder != null) {
            //释放绑定的view
            unbinder.unbind();
        }
    }
}
