package com.cbt.weixin6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;

import com.cbt.weixin6.defined_view.ChangeColorIconWithText;
import com.cbt.weixin6.fragments.FirstFragment;
import com.cbt.weixin6.fragments.FourthFragment;
import com.cbt.weixin6.fragments.SecondFragment;
import com.cbt.weixin6.fragments.ThirdFragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {
    private ViewPager mViewPager ;
    private List<Fragment> mTabs;
    private FragmentPagerAdapter mAdapter;
    private List<ChangeColorIconWithText> mIndicators = new ArrayList<ChangeColorIconWithText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowButtonAlways();
        getActionBar().setDisplayShowHomeEnabled(false);

        initViews();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        initEvent();
    }

    private void initEvent() {
        mViewPager.setOnPageChangeListener(this);
    }

    private void initDatas() {
        mTabs = new ArrayList<Fragment>();
        mTabs.add(new FirstFragment());
        mTabs.add(new SecondFragment());
        mTabs.add(new ThirdFragment());
        mTabs.add(new FourthFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

        ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
        ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
        ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
        ChangeColorIconWithText four = (ChangeColorIconWithText) findViewById(R.id.id_indicator_four);

        mIndicators.add(one);
        mIndicators.add(two);
        mIndicators.add(three);
        mIndicators.add(four);

        for (ChangeColorIconWithText view : mIndicators) {
            view.setOnClickListener(this);
        }
        one.setIconAlpha(1.0f);
    }

    @Override
    public void onClick(View v) {
        resetAllTabs();
        switch (v.getId()) {
            case R.id.id_indicator_one:
                mIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_two:
                mIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_three:
                mIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.id_indicator_four:
                mIndicators.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    //重置所有Tab的颜色
    private void resetAllTabs() {
        for (ChangeColorIconWithText view : mIndicators) {
            view.setIconAlpha(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    private void setOverflowButtonAlways()
    {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mIndicators.get(position);
            ChangeColorIconWithText right = mIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
