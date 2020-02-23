package com.example.projecttwo.interfaces;

import com.example.projecttwo.models.bean.IndexBean;

/**
 * home的契约类
 */
public interface HomeConstract {
    interface View extends IBaseView{
        //获得返回的数据(V层,)
        void getHomeDataReturn(IndexBean result);
    }

    interface Presenter extends IBasePresenter<View>{
        //请求数据(P层,M层调用)
        void getHomeData();
    }
}
