package com.example.projecttwo.interfaces.cart;

import com.example.projecttwo.interfaces.IBasePresenter;
import com.example.projecttwo.interfaces.IBaseView;
import com.example.projecttwo.models.bean.CartBean;
import com.example.projecttwo.models.bean.CartGoodsCheckBean;
import com.example.projecttwo.models.bean.CartGoodsDeleteBean;
import com.example.projecttwo.models.bean.CartGoodsUpdateBean;

public interface ShoppingConstact {

    interface View extends IBaseView {
        void getCartIndexReturn(CartBean result);
        //设置购物车商品数据选中状态的返回
        void setCartGoodsCheckedReturn(CartGoodsCheckBean result);
        //更新购物车类表商品数据返回
        void updateCartGoodsReturn(CartGoodsUpdateBean result);
        //删除商品返回
        void deleteCartGoodsReturn(CartGoodsDeleteBean result);
    }

    interface Presenter extends IBasePresenter<View> {
        void getCartIndex();
        //设置购物车商品数据
        void setCartGoodsChecked(String pids,int isChecked);
        //更新购物车列表的商品数据
        void updateCartGoods(String pids,String goodsId,int number,int id);
        //删除商品
        void deleteCartGoods(String pids);
    }
}
