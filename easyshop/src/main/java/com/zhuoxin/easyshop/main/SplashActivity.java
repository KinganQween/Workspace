package com.zhuoxin.easyshop.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.commons.ConvertUser;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.User;
import com.zhuoxin.easyshop.user.login.LoginPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        activityUtils=new ActivityUtils(this);//存储此activity
        EventBus.getDefault().register(this);//注册EventBus
        //账号冲突踢出
        if (getIntent().getBooleanExtra("AUTO_LOGIN",false)){
            //清除本地缓存的用户信息
            CachePreferences.clearAllData();
            //踢出时，退出环信
            HxUserManager.getInstance().asyncLogout();
        }
        //环信自动登录的
        if (CachePreferences.getUser().getName()!=null&&!HxUserManager.getInstance().isLogin()){
            User user=CachePreferences.getUser();
            EaseUser easeUser= ConvertUser.convert(user);
            HxUserManager.getInstance().asyncLogin(easeUser,user.getPassword());
            Log.d("TAG","login");
            return;
        }
        Timer timer=new Timer();//定时器
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activityUtils.startActivity(MainActivity.class);//跳转到MainActivity中
                Log.d("TAG","run");
                finish();
            }
        },3000);//1.5秒之后执行run方法
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //判断是否登录成功
        if (event.type!= HxEventType.LOGIN)return;//登录不成功
        activityUtils.startActivity(MainActivity.class);
        Log.d("TAG","onEvent");
        finish();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        //判断是否是登录失败的事件
        if (event.type!=HxEventType.LOGIN)return;
        throw new RuntimeException("login error");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//撤销注册
    }
}
