package com.example.projecttwo.interfaces.sort;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortDetailGoodsBean;
import com.example.projecttwo.models.bean.SortDetailTabBean;
import com.example.projecttwo.models.bean.SortGoodsBean;

/**
 * 分类模块(SortFragment)的契约类
 */
public interface SortConstract {
    //V层接口
    interface View extends IBaseView {
        //获得返回的数据()
        void getSortDataReturn(SortBean result);

        void getCurrentSortDataReturn(SortGoodsBean result);
    }

    //P层接口(V层的P层对象调用P层接口的方法,执行P层实现类(SortPresenter)中的方法)
    interface Presenter extends IBasePresenter<View> {
        //请求数据(页面tab页的数据)
        void getSortData();

        //请求数据(页面右侧数据),传入参数id区分不同的数据
        void getCurrentSortData(int id);
    }

    interface DetailView extends IBaseView {

        void getSortDetailTabReturn(SortDetailTabBean result);

        void getSortDetailGoodsReturn(SortDetailGoodsBean result);

    }

    interface DetailPersenter extends IBasePresenter<DetailView> {

        //获取分类详情页的导航列表数据
        void getSortDetailTab(int id);

        //获取分类详情页当前的商品列表数据
        void getSortDetailGoods(int id, int page, int size);
    }
}
