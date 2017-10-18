package com.edu.nbl.newsprojectdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.fragment.CollectFragment;
import com.edu.nbl.newsprojectdemo.fragment.CommentFragment;
import com.edu.nbl.newsprojectdemo.fragment.MyFragment;
import com.edu.nbl.newsprojectdemo.fragment.NewsFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_tb_news)
    TextView mTvTbNews;
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;
    @BindViews({R.id.tv_news, R.id.tv_comment, R.id.collect, R.id.tv_my})
    TextView[] mTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewpager.setAdapter(mAdapter);
        mViewpager.setCurrentItem(0);

        mViewpager.setOnPageChangeListener(mListener);
    }

    FragmentStatePagerAdapter mAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new NewsFragment();
                case 1:
                    return new CommentFragment();
                case 2:
                    return new CollectFragment();
                case 3:
                    return new MyFragment();
            }
            return null;
        }
    };
    ViewPager.OnPageChangeListener mListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (TextView mTextView:mTv){
                mTextView.setSelected(false);
                mTextView.setTextColor(Color.BLACK);
            }
            mTv[position].setSelected(true);
            mTv[position].setTextColor(Color.WHITE);
            mTvTbNews.setText(mTv[position].getText().toString());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @OnClick({R.id.tv_news, R.id.tv_comment, R.id.collect, R.id.tv_my})
    public void onViewClicked(TextView view) {
       for (int i=0;i<mTv.length;i++){
           mTv[i].setSelected(false);
           mTv[i].setTextColor(Color.BLACK);
           mTv[i].setTag(i);
       }
       view.setTextColor(getResources().getColor(R.color.global_bg_color));
        view.setSelected(true);
       mViewpager.setCurrentItem((Integer) view.getTag(),true);
    }
}
