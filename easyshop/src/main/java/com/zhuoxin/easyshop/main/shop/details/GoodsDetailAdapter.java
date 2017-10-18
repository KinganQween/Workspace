package com.zhuoxin.easyshop.main.shop.details;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhuoxin.easyshop.main.shop.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 * 实现商品详情页的图片滑动
 */
public class GoodsDetailAdapter extends PagerAdapter{
    private ArrayList<ImageView> list;//放商品详情的轮播图
    public  GoodsDetailAdapter(ArrayList<ImageView> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //把imageView放入ViewPager容器中
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=list.get(position);//根据下表获取对应的ImageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/7/18 0018 跳转到 GoodsDetailInfoActivity页面
                if(listener!=null) {
                    listener.onItemClick();//回调GoodsDetailInfoActivity重写的onItemClick方法
                }
            }
        });
        container.addView(imageView);//向容器中添加imageView
        return imageView;//返回该显示的imageView
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
    //viewPager的item的点击事件
    private OnItemClickListener listener;//声明成员变量
    public interface OnItemClickListener{
        void onItemClick();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
