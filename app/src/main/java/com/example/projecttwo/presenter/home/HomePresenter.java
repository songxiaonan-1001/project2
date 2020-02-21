package com.example.projecttwo.presenter.home;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.home.HomeConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.IndexBean;
import com.example.projecttwo.utils.RxUtils;

public class HomePresenter extends BasePresenter<HomeConstract.View> implements HomeConstract.Presenter {
    //获取home页的数据
    @Override
    public void getHomeData() {
        addSubscribe(HttpManager.getInstance().getShopApi().getIndexData()
                .compose(RxUtils.<IndexBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<IndexBean>(mView) {

                    @Override
                    public void onNext(IndexBean indexBean) {
                        mView.getHomeDataReturn(indexBean);
                    }
                }));
    }
}
