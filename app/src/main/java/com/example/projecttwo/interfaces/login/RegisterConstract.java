package com.example.projecttwo.interfaces.login;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.VerifyBean;

/**
 * 注册契约类
 */
public interface RegisterConstract {
    interface View extends IBaseView {
        void getVerifyReturn(VerifyBean result);//验证方法返回的结果
    }

    interface Persenter extends IBasePresenter<View> {
        void getVerify();//验证方法
    }
}
