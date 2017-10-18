package com.edu.nbl.eshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.nbl.eshop.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
/*
* 1.点击跳转---跳转
* 2.三秒后自动跳转
* 3.点击跳转时，要取消计时跳转
* 4.跳转完成时，finish当前页面
* 5.重写返回键
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_splash)
    TextView mTvSplash;
    private Unbinder mBind;
    private Timer mTimer;//计时器任务
    private int i=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBind = ButterKnife.bind(this);
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       mTvSplash.setText("跳转（ "+--i+" )");
                        if (i==0){
                            jump();
                        }

                    }
                });
            }
        },0,1000);//0-->0秒的时候执行，1000-->1秒执行周期
    }


    @OnClick(R.id.tv_splash)
    public void onViewClicked() {
        jump();
    }
    public void jump(){
        mTimer.cancel();//跳转时，计时器取消
        Intent mIntent=new Intent();
        mIntent.setClass(this,MainActivity.class);
        startActivity(mIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        mTimer.cancel();
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        mTimer=null;
        super.onDestroy();
    }
}
