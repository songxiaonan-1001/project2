package com.example.projecttwo.presenter.sort;

import com.example.projecttwo.base.BasePresenter;
import com.example.projecttwo.common.CommonSubscriber;
import com.example.projecttwo.interfaces.sort.SortConstract;
import com.example.projecttwo.models.HttpManager;
import com.example.projecttwo.models.bean.SortDetailGoodsBean;
import com.example.projecttwo.models.bean.SortDetailTabBean;
import com.example.projecttwo.utils.RxUtils;

public class SortDetailPresenter extends BasePresenter<SortConstract.DetailView> implements SortConstract.DetailPersenter {

    @Override
    public void getSortDetailTab(int id) {
        addSubscribe(HttpManager.getInstance().getShopApi().getSortDetailTab(id)
                .compose(RxUtils.<SortDetailTabBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortDetailTabBean>(mView) {
                    @Override
                    public void onNext(SortDetailTabBean sortDetailTabBean) {
                        if (sortDetailTabBean.getErrno() == 0) {
                            mView.getSortDetailTabReturn(sortDetailTabBean);
                        } else {
                            mView.showTips(sortDetailTabBean.getErrmsg());
                        }
                    }
                }));
    }

    @Override
    public void getSortDetailGoods(int id, int page, int size) {
        addSubscribe(HttpManager.getInstance().getShopApi().getSortDetailGoods(id, page, size)
                .compose(RxUtils.<SortDetailGoodsBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SortDetailGoodsBean>(mView) {
                    @Override
                    public void onNext(SortDetailGoodsBean sortDetailGoodsBean) {
                        mView.getSortDetailGoodsReturn(sortDetailGoodsBean);
                       /* if (sortDetailGoodsBean.getErrno() == 0) {
                            mView.getSortDetailGoodsReturn(sortDetailGoodsBean);
                        } else {
                            mView.showTips(sortDetailGoodsBean.getErrmsg());
                        }*/
                    }
                }));
    }

}