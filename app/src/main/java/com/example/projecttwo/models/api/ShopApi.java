package com.example.projecttwo.models.api;

import com.example.projecttwo.models.bean.BrandBean;
import com.example.projecttwo.models.bean.IndexBean;
import com.example.projecttwo.models.bean.SortBean;
import com.example.projecttwo.models.bean.SortGoodsBean;

import java.util.function.DoubleUnaryOperator;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShopApi {

    //首页
    @GET("index")
    Flowable<IndexBean> getIndexData();

    //品牌直供的详情页数据接口
    @GET("brand/detail")
    Flowable<BrandBean> getBrandInfo(@Query("id") String id);

    //品牌直供详情的商品列表数据接口
    //@GET("goods/list")
    //Flowable<BrandGoodsBean> getBrandGoods(@Query("brandId") String brandId,@Query("page") int page,@Query("size") int size);

    //获取分类的接口
    @GET("catalog/index")
    Flowable<SortBean> getSortData();

    //获取分类页面的商品数据
    @GET("catalog/current")
    Flowable<SortGoodsBean> getCurrentSortData(@Query("id") int id);
}
