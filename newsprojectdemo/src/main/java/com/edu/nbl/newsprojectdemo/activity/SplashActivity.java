package com.edu.nbl.newsprojectdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.edu.nbl.newsprojectdemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
private TextView mTvsplash;
    private Timer mTimer;
    private int i=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mTvsplash=(TextView) findViewById(R.id.tv_splash);
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvsplash.setText("跳转（"+--i+")");
                        if (i==0){
                            jump();
                        }
                    }
                });
            }
        },0,1000);
    }

    public void MyClick(View view) {
       jump();
    }

    private void jump() {
        mTimer.cancel();//跳转时，计时器取消
        Intent mIntent = new Intent();
        mIntent.setClass(this,MainActivity.class);
        startActivity(mIntent);
        finish();
    }
}
