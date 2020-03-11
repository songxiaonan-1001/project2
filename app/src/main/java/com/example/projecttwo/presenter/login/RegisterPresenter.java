package com.example.projecttwo.presenter.login;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.login.RegisterConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.VerifyBean;
import com.example.projecttwo.utils.RxUtils;

public class RegisterPresenter extends BasePresenter<RegisterConstract.View> implements RegisterConstract.Persenter {
    @Override
    public void getVerify() {
        addSubscribe(HttpManager.getInstance()
                .getShopApi()
                .getVerify()
                .compose(RxUtils.<VerifyBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<VerifyBean>(mView) {
                    @Override
                    public void onNext(VerifyBean verifyBean) {
                        mView.getVerifyReturn(verifyBean);
                    }
                }));
    }

}

