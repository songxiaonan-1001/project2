package com.example.projecttwo.interfaces.login;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.VerifyBean;

public interface RegisterConstract {
    interface View extends IBaseView {
        void getVerifyReturn(VerifyBean result);
    }

    interface Persenter extends IBasePresenter<View> {
        void getVerify();
    }
}
