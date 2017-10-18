package com.edu.nbl.newsprojectdemo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nbl.newsprojectdemo.R;
import com.edu.nbl.newsprojectdemo.entity.News;
import com.edu.nbl.newsprojectdemo.entity.NewsEvent;
import com.edu.nbl.newsprojectdemo.utils.NewsDBUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.pro)
    ProgressBar mPro;
    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);
        getOverflowMenu();
        mNews = (News) getIntent().getExtras().getSerializable("News");
        initTitle();
        initWebView();
    }

    private void getOverflowMenu() {
        ViewConfiguration mConfig=ViewConfiguration.get(this);//得到一个已经设置好设备的显示密度的对象
        try {
            Field menuKeyField=ViewConfiguration.class
                    .getDeclaredField("sHasPerManentMenuKey");
                if (menuKeyField!=null){
                    //强制设置参数，让其重绘三个点
                    menuKeyField.setAccessible(true);
                    menuKeyField.setBoolean(mConfig,false);
                }


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 填充menu的main.xml文件; 给action bar添加条目
        getMenuInflater().inflate(R.menu.menu_main,menu);
//        menu.add(0,0,1,"收藏");// 相当于在res/menu/main.xml文件中，给menu增加一个新的条目item，这个条目会显示title标签的文字（如备注1）
//        menu.add(0,1,2,"分享");//第二个参数代表唯一的item ID.

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (NewsDBUtils.insert(NewsDetailsActivity.this,mNews)==false){
            menu.add(0,0,0,"已收藏");
        }
        else if (NewsDBUtils.deleteOne(NewsDetailsActivity.this,mNews)==true){
            getMenuInflater().inflate(R.menu.menu_main,menu);
        }

        return true;
    }

    private void initTitle() {
        setSupportActionBar(mToolbar);
        ActionBar mAciontBar=getSupportActionBar();
        mAciontBar.setDisplayHomeAsUpEnabled(true);
        mAciontBar.setTitle("");
        mTitle.setText(mNews.title);
    }

    private void initWebView() {


        Log.e("TAG",mNews.link);
        WebSettings mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);//允许使用JavaScript，脚本语言
        //适配屏幕
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setUseWideViewPort(true);

        mWebview.setWebChromeClient(mChromeClient);
        mWebview.setWebViewClient(new WebViewClient());



        //加载网页
        mWebview.loadUrl(mNews.link);
    }
    WebChromeClient mChromeClient=new WebChromeClient(){
        //进度条改变时调用
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            //newProgress最新的进度
            mPro.setProgress(newProgress);
            if (mPro.getProgress()==100){
                //让进度条消失
                mPro.setVisibility(View.GONE);
            }
        }
    };
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                NewsDetailsActivity.this.finish();
                break;
             case 0:
            case R.id.collect:
                saveNews();
                EventBus.getDefault().post(new NewsEvent());
                break;
            case 1:

                break;
        }
        return true;
    }

    private void saveNews() {
        if (NewsDBUtils.insert(NewsDetailsActivity.this,mNews)){
            Toast.makeText(NewsDetailsActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
        }else if (NewsDBUtils.deleteOne(NewsDetailsActivity.this,mNews)){
            Toast.makeText(NewsDetailsActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
        }
    }
}
