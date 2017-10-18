package com.zhuoxin.easyshop.main.me.goodsupdate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsUploadImageShowActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_goods_phone)
    ImageView ivGoodsPhone;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_upload_image_show);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //实现返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //获取传过来的上传商品的图片
        Bitmap bitmap=getIntent().getParcelableExtra("images");
        ivGoodsPhone.setImageBitmap(bitmap);
    }
    //返回键的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
