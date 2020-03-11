package com.example.projecttwo.ui.sort;


import android.content.Intent;
import android.graphics.Color;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.base.BaseFragment;
import com.example.projecttwo.interfaces.sort.SortConstract;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;
import com.example.projecttwo.presenter.sort.SortPresenter;
import com.example.projecttwo.ui.sort.activity.SortDetailActivity;
import com.example.projecttwo.ui.sort.adapter.SortGoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * 分类页面的fragment
 */
public class SortFragment extends BaseFragment<SortConstract.Presenter> implements SortConstract.View,
        VerticalTabLayout.OnTabSelectedListener, BaseAdapter.ItemClickHandler {

    //butterknife绑定
    @BindView(R.id.ver_tablayout_sort)
    VerticalTabLayout verticalTab;//左侧的竖导航控件
    @BindView(R.id.sort_layout_left)
    ConstraintLayout layoutLeft;//左侧包裹竖导航的约束布局
    @BindView(R.id.img_sort)
    ImageView imgSort;//右侧顶部的图片控件(做背景)
    @BindView(R.id.txt_name)
    TextView txtName;//右侧顶部图片中的文本控件
    @BindView(R.id.txt_title)
    TextView txtTitle;//右侧显示页面选中的标题文本控件
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//右侧显示数据的列表控件

    //集合数据
    List<SortBean.DataBean.CategoryListBean> list_sort;//详情页的数据集合
    List<String> titles;//分类列表标签
    List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> goodsList;//分类右边数据

    //适配器
    SortGoodsAdapter sortGoodsAdapter;

    /**
     * 获取页面的xml布局
     * @return
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_sort;
    }

    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        titles = new ArrayList<>();//标题集合
        goodsList = new ArrayList<>();//数据集合
        //竖导航设置选中监听
        verticalTab.addOnTabSelectedListener(this);
        //创建适配器
        sortGoodsAdapter = new SortGoodsAdapter(goodsList, context);
        //创建并设置网格布局管理器(3格)
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        //设置适配器
        recyclerView.setAdapter(sortGoodsAdapter);
        //调用适配器的接口回调方法
        sortGoodsAdapter.setItemClickHandler(this);
    }

    /**
     * 请求数据
     */
    @Override
    protected void initData() {
        presenter.getSortData();//请求数据
    }

    /**
     * 创建P层对象,方法的参数类型SortConstract.Presenter直接用于BaseFragment中的泛型:presenter(即P层对象)
     * 这种写法的目的便于抽取(即其他fragment模块实现基类)
     * @return
     */
    @Override
    protected SortConstract.Presenter createPresenter() {
        return new SortPresenter();
    }

    //创建竖导航的tabadapter(此处使用的是第三方的代码)
    TabAdapter tabAdapter = new TabAdapter() {
        //获取数据的总数
        @Override
        public int getCount() {
            return titles.size();
        }

        @Override
        public ITabView.TabBadge getBadge(int position) {
            return null;
        }

        @Override
        public ITabView.TabIcon getIcon(int position) {
            return null;
        }

        //设置数据
        @Override
        public ITabView.TabTitle getTitle(int position) {
            QTabView.TabTitle title = new QTabView.TabTitle.Builder()
                    .setContent(titles.get(position))//设置数据   也有设置字体颜色的方法
                    .setTextColor(R.color.colorAccent, R.color.colorText)
                    .build();
            return title;
        }

        //设置背景颜色
        @Override
        public int getBackground(int position) {
            return Color.parseColor("#D81B60");
        }
    };

    /**
     * 接收返回的数据
     * @param result
     */
    @Override
    public void getSortDataReturn(SortBean result) {
        //接收返回到数据
        list_sort = result.getData().getCategoryList();
        titles.clear();
        //筛选竖导航的标题数据
        for (SortBean.DataBean.CategoryListBean item : result.getData().getCategoryList()) {
            int id = item.getId();
            titles.add(item.getName());
        }
        verticalTab.setTabAdapter(tabAdapter);
        //更新页面右侧标题数据
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());

        //获取右侧列表数据
        List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        for (SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean item : result.getData().getCurrentCategory().getSubCategoryList()) {
            SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean object = new SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean();
            object.setName(item.getName());
            object.setFront_desc(item.getFront_desc());
            object.setId(item.getId());
            object.setIcon_url(item.getIcon_url());
            object.setWap_banner_url(item.getWap_banner_url());
            list.add(object);
        }
        sortGoodsAdapter.updata(list);//加载数据
    }

    /**
     * 获取并设置数据(页面右侧图片,标题,副标题)
     * @param imgUrl
     * @param name
     * @param title
     */
    private void updateInfo(String imgUrl, String name, String title) {
        Glide.with(context).load(imgUrl).into(imgSort);
        txtName.setText(name);
        txtTitle.setText(title);
    }

    /**
     * 接收当前分类数据返回的方法
     * @param result
     */
    @Override
    public void getCurrentSortDataReturn(SortGoodsBean result) {
        //更新页面右侧标题数据
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());
        List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        list.addAll(result.getData().getCurrentCategory().getSubCategoryList());
        sortGoodsAdapter.updata(list);
    }

    //左边的竖导航选中的方法
    @Override
    public void onTabSelected(TabView tab, int position) {
        if (position < list_sort.size()) {
            int id = list_sort.get(position).getId();
            //请求当前数据
            presenter.getCurrentSortData(id);
        }
    }

    //左边的竖导航未选中的方法
    @Override
    public void onTabReselected(TabView tab, int position) {

    }

    //分类商品列表的item点击回调
    @Override
    public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
        int id = goodsList.get(position).getId();
        //点击进入商品的详情页,同时传入id
        Intent intent = new Intent(context, SortDetailActivity.class);
        intent.putExtra("sortId",id);
        startActivity(intent);
    }
}
