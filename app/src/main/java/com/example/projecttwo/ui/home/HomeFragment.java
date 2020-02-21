package com.example.projecttwo.ui.home;


import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttwo.R;
import com.example.projecttwo.base.BaseAdapter;
import com.example.projecttwo.base.BaseFragment;
import com.example.projecttwo.interfaces.home.HomeConstract;
import com.example.projecttwo.models.bean.IndexBean;
import com.example.projecttwo.presenter.home.HomePresenter;
import com.example.projecttwo.ui.home.activity.BrandActivity;
import com.example.projecttwo.ui.home.adapter.BrandAdapter;
import com.example.projecttwo.ui.home.adapter.GlideImageLoader;
import com.example.projecttwo.ui.home.adapter.NewGoodsListAdapter;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * <HomeConstract.Presenter>作用:为了能调用到契约类中请求数据的方法,同时又是BaseFragment中P层的实现
 */
public class HomeFragment extends BaseFragment<HomeConstract.Presenter> implements HomeConstract.View, BaseAdapter.ItemClickHandler {

    //butterknife绑定
    @BindView(R.id.banner_home)
    Banner banner;
    @BindView(R.id.tablayout_home)
    TabLayout tabLayout_home;
    @BindView(R.id.rec_brand)
    RecyclerView rec_brand;
    @BindView(R.id.rec_category)
    RecyclerView rec_category;
    @BindView(R.id.rec_hotgoods)
    RecyclerView rec_hotgoods;
    @BindView(R.id.rec_newgoods)
    RecyclerView rec_newgoods;
    @BindView(R.id.rec_topic)
    RecyclerView rec_topic;

    //集合数据
    List<IndexBean.DataBean.BannerBean> list_banner;
    List<IndexBean.DataBean.ChannelBean> list_channel;
    List<IndexBean.DataBean.NewGoodsListBean> list_newGoodsList;
    List<IndexBean.DataBean.HotGoodsListBean> list_hotGoodsList;
    List<IndexBean.DataBean.BrandListBean> list_brand;
    List<IndexBean.DataBean.TopicListBean> list_topicList;
    List<IndexBean.DataBean.CategoryListBean> list_categoryList;

    //适配器
    //private ChannelAdapter channelAdapter;
    private NewGoodsListAdapter newGoodsListAdapter;
    //private HotGoodsAdapter hotGoodsListAdapter;
    private BrandAdapter brandAdapter;
    //private TopicListAdapter topicListAdapter;
    //private CategoryListAdapter categoryListAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //创建数据集合
        list_brand = new ArrayList<>();//品牌制造商直供
        list_channel = new ArrayList<>();//tablayout数据
        list_newGoodsList = new ArrayList<>();//新品首发
        list_hotGoodsList = new ArrayList<>();//人气推荐
        list_topicList = new ArrayList<>();//专题精选
        list_categoryList = new ArrayList<>();//分类数据:居家,餐厨等
        //创建适配器
        brandAdapter = new BrandAdapter(list_brand, context);
        newGoodsListAdapter = new NewGoodsListAdapter(list_newGoodsList, context);
        //创建并设置网格管理器(brand专题)
        rec_brand.setLayoutManager(new GridLayoutManager(context, 2));
        rec_newgoods.setLayoutManager(new GridLayoutManager(context, 2));
        //设置适配器
        rec_brand.setAdapter(brandAdapter);
        rec_newgoods.setAdapter(newGoodsListAdapter);
        brandAdapter.setItemClickHandler(new BrandAdapter.ItemClickHandler() {
            @Override
            public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
                Toast.makeText(context, "点击了专题栏", Toast.LENGTH_SHORT).show();
            }
        });
        newGoodsListAdapter.setItemClickHandler(new BrandAdapter.ItemClickHandler() {
            @Override
            public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
                Toast.makeText(context, "点击了新品栏", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initData() {
        //此处的presenter为BaseFragment中的P层对象
        presenter.getHomeData();
    }

    @Override
    protected HomeConstract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void getHomeDataReturn(IndexBean result) {//返回的数据
        //banner图片数据
        list_banner = result.getData().getBanner();
        List<String> images = new ArrayList<>();
        for (int i = 0; i < list_banner.size(); i++) {
            images.add(list_banner.get(i).getImage_url());
        }
        banner.setImageLoader(new GlideImageLoader());//设置图片加载器
        banner.setImages(images);//设置图片集合
        banner.start();

        //tablayout数据
        for (int i = 0; i < result.getData().getCategoryList().size(); i++) {
            tabLayout_home.addTab(tabLayout_home.newTab().setText(result.getData().getCategoryList().get(i).getName()));
        }

        //刷新Brand列表数据
        brandAdapter.updata(result.getData().getBrandList());
        //刷新新品发布列表数据
        newGoodsListAdapter.updata(result.getData().getNewGoodsList());

    }

    /**
     * 接口回调的方法
     *
     * @param position
     * @param holder
     */
    @Override
    public void itemClick(int position, BaseAdapter.BaseViewHolder holder) {
        IndexBean.DataBean.BrandListBean bean = list_brand.get(position);
        //((TextView) holder.getView(R.id.txt_name)).setText(bean.getName() + "新的名字");
        Log.i("brand-click", String.valueOf(position));
        //跳转到brand详情页
        Intent intent = new Intent(getContext(), BrandActivity.class);
        intent.putExtra("brandId", bean.getId());
        startActivity(intent);
    }
}
