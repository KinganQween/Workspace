package com.edu.nbl.eshop.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.fragment.MeFragment;
import com.edu.nbl.eshop.fragment.ShopFragment;
import com.edu.nbl.eshop.fragment.UnLoginFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *1:viewpager设置适配器
 * 2：viewpager滑动监听
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.viewpager)
    ViewPager mVp;
    @BindViews({R.id.tv_shop, R.id.tv_message, R.id.tv_contact, R.id.tv_me})
    TextView[] mTvs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(0);
        mVp.addOnPageChangeListener(mListener);
        mTvs[0] .setSelected(true);
        mTvs[0].setTextColor(getResources().getColor(R.color.colorPrimary));
    }
    FragmentStatePagerAdapter mAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {


        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ShopFragment();
                case 1:
                    return new UnLoginFragment();
                case 2:
                    return new UnLoginFragment();
                case 3:
                    return new MeFragment();
            }
            return null;
        }
        @Override
        public int getCount() {
            return 4;
        }
    };
    ViewPager.OnPageChangeListener mListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //滑动结束后，滑动到哪一页，那一页变色
            //首先让所有的颜色变成默认颜色
            for (TextView tv:mTvs) {
                tv.setTextColor(getResources().getColor(R.color.text_goods_describe));
                //改变图片状态没有选中
                tv.setSelected(false);

            }
            mTvs[position] .setSelected(true);
            mTvs[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private long curTime = 0;//记录下第一次安县返回键的当前系统时间

    @Override
    public void onBackPressed() {
        //用当前时间减去自己声明的时间，判断时间差
        if (System.currentTimeMillis() - curTime > 2000) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            //将当前系统的时间赋值给自己声明的时间
            curTime = System.currentTimeMillis();
        } else finish();
    }


    @OnClick({R.id.tv_shop, R.id.tv_message, R.id.tv_contact, R.id.tv_me})
    public void onViewClicked(View view) {
        for (int i=0;i<mTvs.length;i++){
            mTvs[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvs[i].setSelected(false);
            mTvs[i].setTag(i);
        }
        mVp.setCurrentItem((Integer) view.getTag(),false);
    }
}
