package com.zhuoxin.easyshop.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.feicuiedu.apphx.presentation.contact.list.HxContactListFragment;
import com.feicuiedu.apphx.presentation.conversation.HxConversationListFragment;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.main.me.MeFragment;
import com.zhuoxin.easyshop.main.shop.ShopFragment;
import com.zhuoxin.easyshop.model.CachePreferences;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    //使用butterknife注解的方式获取控件
    //初始化底部四个TextView
    @BindViews({R.id.tv_shop,R.id.tv_message,R.id.tv_contact,R.id.tv_me})
    TextView[] textViews;
    @BindView(R.id.main_toolbar)
    Toolbar toobar;//初始化toobar
    @BindView(R.id.tv_title)
    TextView tv_title;//ttolbar里的标题
    @BindView(R.id.viewpager)
    ViewPager viewPager;//初始化viewPager
    private ActivityUtils activityUtils;//操作Activity的工具类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);//绑定activity也就是说从MainActivity里获取控件
        activityUtils=new ActivityUtils(this);//通过弱引用存储MainActivity
        init();//初始化视图

    }
    private void init(){
        //判断用户登录
        if (CachePreferences.getUser().getName()==null){//没有登录过
            viewPager.setAdapter(unLoginAdapter);//向viewpager中添加未登录的适配器
            viewPager.setCurrentItem(0);//viewPager显示在商品页
        }else{//登录过的
            viewPager.setAdapter(loginAdapter);//向viewpager中添加登录过的适配器e
            viewPager.setCurrentItem(0);//viewPager显示在商品页
        }
        //textViews[0]市场的TextView
        textViews[0].setSelected(true);//一来市场是有色的
        //给viewPager添加滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (TextView textView:textViews){//遍历底部四个TextView
                    textView.setSelected(false);//都变成黑的
                }
                textViews[position].setSelected(true);//把单独滑到的页面对应的TextView变成有色的
                tv_title.setText(textViews[position].getText().toString());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    //用户登录的适配器
    private FragmentStatePagerAdapter loginAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()){
        @Override
        public Fragment getItem(int postion){//获取ViewPager中对应位置的Fragment

            switch(postion){
                case 0://当位置是0时显示"市场"的Fragment
                    return new ShopFragment();
                case 1://当位置是1显示"信息"的Fragment
                    return new HxConversationListFragment();
                case 2://当位置是2显示"通讯录"的Fragment
                    return new HxContactListFragment();
                case 3://当位置是3显示"我的"Fragment
                    return new MeFragment();
            }
            return null;
        }

        @Override
        public int getCount(){
            return 4;//有四个Fragment
        }
    };
    //用户未登录 消息和通讯录显示未登录状态
    private FragmentStatePagerAdapter unLoginAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0://市场
                    return new ShopFragment();
                case 1://消息
                    return new UnLoginFragment();
                case 2://通讯录
                    return new UnLoginFragment();
                case 3://我的
                    return new MeFragment();
            }
            return null;
        }
    };
    @OnClick({R.id.tv_shop,R.id.tv_message,R.id.tv_contact,R.id.tv_me})
    public void onClick(TextView textView){//textView标示点谁指谁
        for (int i=0;i<textViews.length;i++){//遍历四个TextView
            textViews[i].setTag(i);//自己存自己的位置  每一个TextView都存储了自己的位置
        }
        //不要滑动效果，第二个参数为false
        viewPager.setCurrentItem((Integer)textView.getTag(),false);
    }
    //按两次返回，退出程序
    private boolean isExit=false;//监听状态的改变

    @Override
    public void onBackPressed() {
        if (!isExit){//第一次不退出  两秒内点击退出
            isExit=true;
            activityUtils.showToast("再摁一次退出程序");
            viewPager.postDelayed(new Runnable(){
                @Override
                public void run(){//两秒后执行
                    isExit=false;
                }
            },2000);
        }else{
            finish();//2秒内再摁一次退出
        }
    }
}
