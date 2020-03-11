package com.example.projecttwo.presenter.cart;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.cart.CartConstart;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.RelatedBean;
import com.example.projecttwo.utils.RxUtils;

public class CartPresenter extends BasePresenter<CartConstart.View> implements CartConstart.Persenter {

    //获取商品购买页面的数据
    @Override
    public void getRelatedData(int id) {
        addSubscribe(HttpManager.getInstance().getShopApi().getRelatedData(id)
                .compose(RxUtils.<RelatedBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<RelatedBean>(mView) {

                    @Override
                    public void onNext(RelatedBean relatedBean) {
                        mView.getRelatedDataReturn(relatedBean);
                    }
                }));
    }

}