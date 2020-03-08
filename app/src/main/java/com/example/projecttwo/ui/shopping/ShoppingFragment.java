package com.example.projecttwo.ui.shopping;


import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.base.BaseFragment;
import com.example.projecttwo.interfaces.cart.ShoppingConstact;
import com.example.projecttwo.models.bean.CartBean;
import com.example.projecttwo.models.bean.CartGoodsCheckBean;
import com.example.projecttwo.models.bean.CartGoodsDeleteBean;
import com.example.projecttwo.models.bean.CartGoodsUpdateBean;
import com.example.projecttwo.presenter.cart.ShoppingPresenter;
import com.example.projecttwo.ui.login.LoginActivity;
import com.example.projecttwo.ui.shopping.adapters.ShoppingAdapter;
import com.example.projecttwo.utils.SpUtils;
import com.example.projecttwo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingFragment extends BaseFragment<ShoppingConstact.Presenter> implements ShoppingConstact.View,
        BaseAdapter.ItemClickHandler {


    @BindView(R.id.radio_all)
    CheckBox radioAll;
    @BindView(R.id.txt_TotalPrice)
    TextView txtTotalPrice;
    @BindView(R.id.txt_order)
    TextView txtOrder;
    @BindView(R.id.txt_edit)
    TextView txtEdit;
    @BindView(R.id.layout_common)
    ConstraintLayout layoutCommon;
    @BindView(R.id.cart_list)
    RecyclerView cartList;

    ShoppingAdapter shoppingAdapter;
    List<CartBean.DataBean.CartListBean> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shoping;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        shoppingAdapter = new ShoppingAdapter(list, context);
        cartList.setLayoutManager(new LinearLayoutManager(context));
        cartList.setAdapter(shoppingAdapter);
        /*radioAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSelectAll(isChecked);
                shoppingAdapter.notifyDataSetChanged();
            }
        });*/
        shoppingAdapter.setItemClickHandler(this);
    }

    @Override
    protected void initData() {
        //如果用户没有登录就要
        String token = SpUtils.getInstance().getString("token");
        if (TextUtils.isEmpty(token)) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, 100);
        } else {
            presenter.getCartIndex();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == 100) {
            if (presenter != null) presenter.getCartIndex();
        }
    }

    @Override
    protected ShoppingConstact.Presenter createPresenter() {
        return new ShoppingPresenter();
    }

    @Override
    public void getCartIndexReturn(CartBean result) {
        shoppingAdapter.updata(result.getData().getCartList());
        //判断当前类别的数据是否是全部选中
        int totalPrice = 0;
        int nums = 0;
        boolean isSelectAll = true;
        for (CartBean.DataBean.CartListBean item : result.getData().getCartList()) {
            if (isSelectAll) {
                if (!item.isSelect) {
                    isSelectAll = false;
                }
            }
            if (item.isSelect) {
                totalPrice += item.getRetail_price() * item.getNumber();
                nums += item.getNumber();
            }
        }
        if (isSelectAll) {
            radioAll.setChecked(true);
        }
        String price = context.getResources().getString(R.string.price_news_model).replace("$", String.valueOf(totalPrice));
        txtTotalPrice.setText(price);
        radioAll.setText("全选(" + nums + ")");
    }

    //设置商品选中返回
    @Override
    public void setCartGoodsCheckedReturn(CartGoodsCheckBean result) {

    }

    //更新商品信息返回
    @Override
    public void updateCartGoodsReturn(CartGoodsUpdateBean result) {
        for (CartGoodsUpdateBean.DataBean.CartListBean item : result.getData().getCartList()) {
            CartBean.DataBean.CartListBean bean = getItemDataById(item.getId());
            if (bean != null) bean.setNumber(item.getNumber());
        }
        shoppingAdapter.notifyDataSetChanged();
    }

    //删除商品返回
    @Override
    public void deleteCartGoodsReturn(CartGoodsDeleteBean result) {
        // int lg = list.size();
        for (int i = 0; i < list.size(); i++) {
            CartBean.DataBean.CartListBean listBean = list.get(i);
            boolean bool = false; //检验当前的list中第i条数据是否被删除
            for (CartGoodsDeleteBean.DataBean.CartListBean item : result.getData().getCartList()) {
                if (item.getId() == listBean.getId()) {
                    bool = true;
                    break;
                }
            }
            //如果不在，删除list中的第i条数据
            if (!bool) {
                list.remove(i);
            }
        }
        shoppingAdapter.notifyDataSetChanged();
    }

    /**
     * 通过商品列表数据id获取对应的商品数据
     *
     * @param id
     * @return
     */
    private CartBean.DataBean.CartListBean getItemDataById(int id) {
        for (CartBean.DataBean.CartListBean item : list) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    @OnClick({R.id.txt_order, R.id.txt_edit, R.id.radio_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_all:
                boolean isChecked = radioAll.isChecked();
                setSelectAll(isChecked);
                shoppingAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_order:
                boolean isEditor = getPageIsEditor();
                if (isEditor) {
                    //当前是编辑状态，执行删除操作
                    deleteGoods();
                } else {
                    //当前是正常状态，执行下单的操作
                    doOrder();
                }
                break;
            case R.id.txt_edit:
                boolean bool = getPageIsEditor();
                if (!bool) {
                    txtEdit.setText("完成");
                    txtOrder.setText("删除所选");
                    shoppingAdapter.isEdit = true;
                    shoppingAdapter.notifyDataSetChanged();
                } else {
                    txtEdit.setText("编辑");
                    txtOrder.setText("下单");
                    shoppingAdapter.isEdit = false;
                    //提交编辑页面的数据
                    shoppingAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 删除商品
     */
    private void deleteGoods() {
        //查找当前需要删除的商品
        StringBuilder sb = new StringBuilder();
        for (CartBean.DataBean.CartListBean item : list) {
            if (item.isDelSelect) {
                sb.append(item.getProduct_id());
                sb.append(",");
            }
        }
        if (sb.length() > 0) {
            //去掉末尾的逗号
            sb.deleteCharAt(sb.length() - 1);
            String pids = sb.toString();
            //调用删除商品的接口
            presenter.deleteCartGoods(pids);
        } else {
            showTips("没有选中任何商品");
        }
    }

    /**
     * 下单
     */
    private void doOrder() {

    }

    /**
     * radio状态变化
     * 接口回调实现的方法
     * 作用：1.处理正常状态下的列表item选中状态的变化
     * 2.处理编辑状态下列表item的数据更新
     */
    @Override
    public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
        boolean bool = getPageIsEditor();
        if (!bool) {
            updateSelectAll();
            //更新商品的选中状态
            int[] ids = new int[1];
            ids[0] = list.get(position).getId();
            int ischecked = list.get(position).isSelect ? 0 : 1;
            updateGoodsChecked(ids, ischecked);
        } else {
            //更新商品的数量
            String pids = String.valueOf(list.get(position).getProduct_id());
            String goodsId = String.valueOf(list.get(position).getGoods_id());
            int number = list.get(position).getNumber();
            int id = list.get(position).getId();
            //发送更新商品数量的请求方法
            presenter.updateCartGoods(pids, goodsId, number, id);
        }
    }

    /**
     * 更新购物车商品数据的选中状态
     *
     * @param ids
     * @param isChecked
     */
    private void updateGoodsChecked(int[] ids, int isChecked) {
        String pids = StringUtils.splitArray(ids);
        presenter.setCartGoodsChecked(pids, isChecked);
    }

    /**
     * 设置全部选中
     */
    private void setSelectAll(boolean bool) {
        //更新列表中商品的状态
        int totalPrice = 0;
        int nums = 0;
        int[] ids = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            list.get(i).isSelect = bool;
            ids[i] = list.get(i).getId();
            if (bool) {
                totalPrice += list.get(i).getRetail_price() * list.get(i).getNumber();
                nums += list.get(i).getNumber();
            }
        }
        int isChecked = bool ? 0 : 1;
        updateGoodsChecked(ids, isChecked);
        //刷新界面数量和总价
        if (bool) {
            String price = context.getResources().getString(R.string.price_news_model).replace("$", String.valueOf(totalPrice));
            txtTotalPrice.setText(price);
            radioAll.setText("全选(" + nums + ")");
        } else {
            txtTotalPrice.setText("");
        }

    }

    /**
     * 检查是否全部选择
     *
     * @return
     */
    private void updateSelectAll() {
        //int[] arr = new int[3]; //用一个数组来记录当前是否全选，选中的商品数量，总的价格
        int totalPrice = 0;
        int nums = 0;
        boolean isAll = true; //是否全选
        for (int i = 0; i < list.size(); i++) {
            if (!shoppingAdapter.isEdit) {
                boolean isSelect = list.get(i).isSelect;
                //记录判断是否全选
                if (isAll) {
                    isAll = isSelect;
                }
                //计算选中的商品数量和价格
                if (isSelect) {
                    nums += list.get(i).getNumber();
                    totalPrice += list.get(i).getNumber() * list.get(i).getRetail_price();
                }
            } else {
                boolean isDelSelect = list.get(i).isDelSelect;
                if (isAll) {
                    isAll = isDelSelect;
                }
                if (isDelSelect) {
                    nums += list.get(i).getNumber();
                }
            }
        }
        radioAll.setChecked(isAll);
        radioAll.setText("全选(" + nums + ")");
        if (!shoppingAdapter.isEdit) {
            String price = context.getResources().getString(R.string.price_news_model);
            price = price.replace("$", String.valueOf(totalPrice));
            txtTotalPrice.setText(price);
        } else {
            txtTotalPrice.setText("");
        }
    }

    /**
     * 获取当前页面是否是编辑状态
     *
     * @return
     */
    private boolean getPageIsEditor() {
        String str = txtEdit.getText().toString();
        return str.equals("编辑") ? false : true;
    }
}
