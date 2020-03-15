package com.example.projecttwo.presenter.sort;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.sort.SortConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;
import com.example.projecttwo.utils.RxUtils;

/**
 * 分类页面(SortFragment)的P层的实现类
 */
public class SortPresenter extends BasePresenter<SortConstract.View> implements SortConstract.Presenter {

    @Override
    public void getSortData() {//分类导航数据的请求
        addSubscribe(HttpManager.getInstance().getShopApi().getSortData()
                .compose(RxUtils.<SortBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortBean>(mView) {
                    @Override
                    public void onNext(SortBean sortBean) {
                        mView.getSortDataReturn(sortBean);//调用分类tab数据返回
                    }
                }));
    }

    @Override
    public void getCurrentSortData(int id) {//分类页面数据的请求
        addSubscribe(HttpManager.getInstance().getShopApi().getCurrentSortData(id)
                .compose(RxUtils.<SortGoodsBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortGoodsBean>(mView) {
                    @Override
                    public void onNext(SortGoodsBean sortGoodsBean) {
                        mView.getCurrentSortDataReturn(sortGoodsBean);//调用分类页面数据返回
                       /* if (sortGoodsBean.getErrno() == 0) {
                            mView.getCurrentSortDataReturn(sortGoodsBean);//调用分类页面数据返回
                        } else {
                            mView.showTips(sortGoodsBean.getErrmsg());//错误信息提示
                        }*/
                    }
                }));
    }
}
