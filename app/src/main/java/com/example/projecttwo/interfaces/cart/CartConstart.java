package com.example.projecttwo.interfaces.cart;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.RelatedBean;

public interface CartConstart {
    interface View extends IBaseView {
        void getRelatedDataReturn(RelatedBean result);
    }

    interface Persenter extends IBasePresenter<View> {
        void getRelatedData(int id);
    }

}
