package com.example.projecttwo.interfaces;

/**
 * P层基类接口
 * @param <V> 定义泛型继承V层基类接口
 */
public interface IBasePresenter<V extends IBaseView> {
    //关联V层
    void attachView(V view);
    //取消V层的关联
    void detachView();
}
