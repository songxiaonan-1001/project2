package com.example.projecttwo.ui.sort;


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
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends BaseFragment<SortConstract.Presenter> implements SortConstract.View,
        VerticalTabLayout.OnTabSelectedListener, BaseAdapter.ItemClickHandler {

    //butterknife绑定
    @BindView(R.id.ver_tablayout_sort)
    VerticalTabLayout verticalTab;//竖导航控件
    @BindView(R.id.sort_layout_left)
    ConstraintLayout layoutLeft;//约束布局
    @BindView(R.id.img_sort)
    ImageView imgSort;//图片控件
    @BindView(R.id.txt_name)
    TextView txtName;//文本控件
    @BindView(R.id.txt_title)
    TextView txtTitle;//文本控件
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//列表控件

    //集合数据
    List<SortBean.DataBean.CategoryListBean> list_sort;
    List<String> titles;//分类列表标签
    List<SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean> goodsList;//分类右边数据

    //适配器
    SortGoodsAdapter sortGoodsAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void initView() {
        titles = new ArrayList<>();//标题集合
        goodsList = new ArrayList<>();//数据集合
        //竖导航设置选中监听
        verticalTab.addOnTabSelectedListener(this);
        //创建适配器
        sortGoodsAdapter = new SortGoodsAdapter(goodsList, context);
        //创建并设置网格布局管理器
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        //设置适配器
        recyclerView.setAdapter(sortGoodsAdapter);
        //调用适配器的接口回调方法
        sortGoodsAdapter.setItemClickHandler(this);
    }

    @Override
    protected void initData() {
        presenter.getSortData();//请求数据
    }

    @Override
    protected SortConstract.Presenter createPresenter() {
        return new SortPresenter();
    }

    //创建竖导航的tabadapter
    TabAdapter tabAdapter = new TabAdapter() {
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

        @Override
        public ITabView.TabTitle getTitle(int position) {
            QTabView.TabTitle title = new QTabView.TabTitle.Builder()
                    .setContent(titles.get(position))//设置数据   也有设置字体颜色的方法
                    .setTextColor(R.color.colorAccent, R.color.colorText)
                    .build();
            return title;
        }

        @Override
        public int getBackground(int position) {
            return Color.parseColor("#D81B60");
        }
    };

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
        //加载右侧图片标题
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());

        //获取右侧列表数据
        List<SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        for (SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean item : result.getData().getCurrentCategory().getSubCategoryList()) {
            SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean object = new SortBean.DataBean.CurrentCategoryBean.SubCategoryListBean();
            object.setName(item.getName());
            object.setFront_desc(item.getFront_desc());
            object.setId(item.getId());
            object.setIcon_url(item.getIcon_url());
            object.setWap_banner_url(item.getWap_banner_url());
            list.add(object);
        }
        sortGoodsAdapter.updata(list);//加载数据
    }

    private void updateInfo(String imgUrl, String name, String title) {
        Glide.with(context).load(imgUrl).into(imgSort);
        txtName.setText(name);
        txtTitle.setText(title);
    }

    @Override
    public void getCurrentSortDataReturn(SortGoodsBean result) {
        updateInfo(result.getData().getCurrentCategory().getBanner_url(),
                result.getData().getCurrentCategory().getFront_desc(),
                result.getData().getCurrentCategory().getName());
        List<SortGoodsBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list = new ArrayList<>();
        list.addAll(result.getData().getCurrentCategory().getSubCategoryList());
        sortGoodsAdapter.updata(list);
    }

    //点击左边的竖导航切换分类数据
    @Override
    public void onTabSelected(TabView tab, int position) {
        if (position < list_sort.size()) {
            int id = list_sort.get(position).getId();
            presenter.getCurrentSortData(id);
        }
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }

    //分类商品列表的item点击回调
    @Override
    public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
        int id = goodsList.get(position).getId();
        //打开商品的详情页
        //Intent intent = new Intent(context, SortDetailActivity.class);
        //intent.putExtra("sortId",id);
        //startActivity(intent);
    }
}
