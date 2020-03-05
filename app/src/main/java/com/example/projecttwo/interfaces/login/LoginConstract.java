package com.example.projecttwo.interfaces.login;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.UserBean;

public interface LoginConstract {
    interface View extends IBaseView {
        void loginReturn(UserBean result);
    }

    interface Presenter extends IBasePresenter<View> {
        void login(String nickname,String password);
    }
}
