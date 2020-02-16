package com.example.projecttwo.interfaces;

public interface IBasePresenter<V extends IBaseView> {
    //关联V层
    void attachView(V view);
    //取消V层的关联
    void detachView();
}
