package com.edu.nbl.textdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindViews({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six})
    TextView[] mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);
        mViewpager.setAdapter(mAdapter);
        mViewpager.addOnPageChangeListener(listener);
        mViewpager.setCurrentItem(0);
    }
    FragmentStatePagerAdapter mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new OneActivity();
                case 1:
                    return new TwoActivity();
                case 2:
                    return new ThreeActivity();
                case 3:
                    return new FourActivity();
                case 4:
                    return new FiveActivity();
                case 5:
                    return new SixActivity();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @OnClick({R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.tv_four, R.id.tv_five, R.id.tv_six})
    public void onViewClicked(View view) {
        for (int i = 0; i < mTv.length; i++) {
            mTv[i].setSelected(false);
            //设置字体颜色为灰色
            mTv[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            //改变图片状态为没有选中状态
            mTv[i].setTag(i);//设置标志
        }
        mViewpager.setCurrentItem((Integer) view.getTag(),false);
    }
}
