package com.zhuoxin.easyshop.main.shop.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.model.AvaterLoadOptions;
import com.zhuoxin.easyshop.network.EasyShopApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class GoodsDetailInfoActivity extends AppCompatActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    ArrayList<String> list;//存放图片地址
    private GoodsDetailAdapter adapter;//viewpager的适配器
    //需要地址
    private static final String IMAGES = "images";
    /**
     * @param context 指的是GoodsDetailActivity
     * @param imgUris 图片地址集合
     * @return
     */
    public static Intent getStartIntent(Context context, ArrayList<String> imgUris) {
        Intent intent = new Intent(context, GoodsDetailInfoActivity.class);
        intent.putExtra(IMAGES, imgUris);//存储图片地址集合
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_info);
        ButterKnife.bind(this);
        list=getIntent().getStringArrayListExtra(IMAGES);//图片地址集合
        adapter=new GoodsDetailAdapter(getImage(list));
        viewpager.setAdapter(adapter);
        adapter.setOnItemClickListener(new GoodsDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick() {
                finish();
            }
        });
        indicator.setViewPager(viewpager);
    }
    private ArrayList<ImageView> getImage(ArrayList<String> list) {
        ArrayList<ImageView> list_image=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            ImageView view=new ImageView(this);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            //将EasyShopApi.IMAGE_URL+list.get(i)地址上的图片放到view上
            ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+list.get(i),view,AvaterLoadOptions.build_item());
            list_image.add(view);
        }
        return list_image;
    }
}
