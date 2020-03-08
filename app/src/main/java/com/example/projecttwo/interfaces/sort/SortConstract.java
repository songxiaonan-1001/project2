package com.example.projecttwo.interfaces.sort;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;

/**
 * sort的契约类
 */
public interface SortConstract {
    interface View extends IBaseView {
        //获得返回的数据(V层,)
        void getSortDataReturn(SortBean result);

        void getCurrentSortDataReturn(SortGoodsBean result);
    }

    interface Presenter extends IBasePresenter<View> {
        //请求数据(P层,M层调用)
        void getSortData();

        void getCurrentSortData(int id);
    }
}
