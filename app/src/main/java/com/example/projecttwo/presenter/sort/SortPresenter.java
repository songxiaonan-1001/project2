package com.example.projecttwo.presenter.sort;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.SortConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;
import com.example.projecttwo.utils.RxUtils;

public class SortPresenter extends BasePresenter<SortConstract.View> implements SortConstract.Presenter {
    @Override
    public void getSortData() {
        addSubscribe(HttpManager.getInstance().getShopApi().getSortData()
                .compose(RxUtils.<SortBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortBean>(mView) {
                    @Override
                    public void onNext(SortBean sortBean) {
                        mView.getSortDataReturn(sortBean);
                    }
                }));
    }

    @Override
    public void getCurrentSortData(int id) {
        addSubscribe(HttpManager.getInstance().getShopApi().getCurrentSortData(id)
                .compose(RxUtils.<SortGoodsBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortGoodsBean>(mView) {
                    @Override
                    public void onNext(SortGoodsBean sortGoodsBean) {
                        mView.getCurrentSortDataReturn(sortGoodsBean);
                    }
                }));
    }
}
