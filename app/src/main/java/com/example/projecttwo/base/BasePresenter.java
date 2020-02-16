package com.example.projecttwo.base;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * P层的实现基类,具备以下特征:
 * 1.关联和解绑V层
 * 2.对网络请求对象进行背压式处理CompositeDisposable??
 *
 * 对第一行代码的理解(泛型):一个抽象类(abstract)BasePresenter,
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    protected V mView;
    //对V层的进行弱引用??
    WeakReference<V> weakReference;

    //RxJava2 背压式网络请求处理
    CompositeDisposable compositeDisposable;

    /**
     * 关联V层
     * @param view
     */
    @Override
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
        mView = weakReference.get();
    }

    /**
     * 把当前业务下的网络请求对象添加到compositedisposable
     * @param disposable
     */
    public void addSubscribe(Disposable disposable){
        if (compositeDisposable ==null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 在界面关闭的时候移除网络请求对象
     */
    public void unSubscribe(){
        compositeDisposable.clear();
    }

    /**
     * 解绑V层
     */
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
