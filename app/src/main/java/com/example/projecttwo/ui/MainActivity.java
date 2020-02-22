package com.example.projecttwo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.projecttwo.R;
import com.example.projecttwo.ui.sort.SortFragment;
import com.example.projecttwo.ui.home.HomeFragment;
import com.example.projecttwo.ui.me.MeFragment;
import com.example.projecttwo.ui.shopping.ShoppingFragment;
import com.example.projecttwo.ui.topic.TopicFragment;
import com.example.projecttwo.utils.NoScrollViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewpagerMain;
    private TabLayout mTablayoutMain;
    private List<Fragment> fragmentList;
    private Toolbar mToolbarMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//初始化布局
        initToolBar();
        initContent();//初始化主内容区域
    }

    //初始化ToolBar
    private void initToolBar() {
        mToolbarMain.setTitle("");
        setSupportActionBar(mToolbarMain);
    }

    private void initContent() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new TopicFragment());
        fragmentList.add(new SortFragment());
        fragmentList.add(new ShoppingFragment());
        fragmentList.add(new MeFragment());
        //创建适配器
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        //设置适配器
        mViewpagerMain.setAdapter(viewPagerAdapter);
        //设置tablayout
        mTablayoutMain.addTab(mTablayoutMain.newTab().setText(R.string.home).setIcon(R.drawable.selector_home));
        mTablayoutMain.addTab(mTablayoutMain.newTab().setText(R.string.topic).setIcon(R.drawable.selector_topic));
        mTablayoutMain.addTab(mTablayoutMain.newTab().setText(R.string.sort).setIcon(R.drawable.selector_sort));
        mTablayoutMain.addTab(mTablayoutMain.newTab().setText(R.string.shopping).setIcon(R.drawable.selector_shopping));
        mTablayoutMain.addTab(mTablayoutMain.newTab().setText(R.string.me).setIcon(R.drawable.selector_me));
        mTablayoutMain.setupWithViewPager(mViewpagerMain);
        mTablayoutMain.getTabAt(0).setText(R.string.home).setIcon(R.drawable.selector_home);
        mTablayoutMain.getTabAt(1).setText(R.string.topic).setIcon(R.drawable.selector_topic);
        mTablayoutMain.getTabAt(2).setText(R.string.sort).setIcon(R.drawable.selector_sort);
        mTablayoutMain.getTabAt(3).setText(R.string.shopping).setIcon(R.drawable.selector_shopping);
        mTablayoutMain.getTabAt(4).setText(R.string.me).setIcon(R.drawable.selector_me);
    }

    private void initView() {
        mViewpagerMain = (ViewPager) findViewById(R.id.viewpager_main);
        mTablayoutMain = (TabLayout) findViewById(R.id.tablayout_main);
        mToolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
    }
}
