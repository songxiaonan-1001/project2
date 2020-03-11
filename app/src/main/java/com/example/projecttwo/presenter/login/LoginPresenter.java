package com.example.projecttwo.presenter.login;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.login.LoginConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.UserBean;
import com.example.projecttwo.utils.RxUtils;

public class LoginPresenter extends BasePresenter<LoginConstract.View> implements LoginConstract.Presenter {
    @Override
    public void login(String nickname, String password) {
        addSubscribe(HttpManager.getInstance().getShopApi()
                .login(nickname,password)
                .compose(RxUtils.<UserBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<UserBean>(mView){

                    @Override
                    public void onNext(UserBean userBean) {
                        if(userBean.getErrno() == 0){
                            mView.loginReturn(userBean);
                        }else{
                            mView.showTips(userBean.getErrmsg());
                        }
                    }
                }));
    }
}
