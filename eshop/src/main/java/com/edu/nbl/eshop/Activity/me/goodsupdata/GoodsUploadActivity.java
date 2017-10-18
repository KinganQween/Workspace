package com.edu.nbl.eshop.Activity.me.goodsupdata;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edu.nbl.eshop.R;
import com.edu.nbl.eshop.commons.ImageUtils;
import com.edu.nbl.eshop.commons.MyFileUtils;
import com.edu.nbl.eshop.components.PicWindow;
import com.edu.nbl.eshop.model.ImageItem;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * toolbar--相片--RecyclerView
 */
public class GoodsUploadActivity extends AppCompatActivity {

    @BindView(R.id.tv_goods_delete)
    TextView mTvGoodsDelete;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_goods_name)
    EditText mEtGoodsName;
    @BindView(R.id.et_goods_price)
    EditText mEtGoodsPrice;
    @BindView(R.id.tv_goods_type)
    TextView mTvGoodsType;
    @BindView(R.id.btn_goods_type)
    Button mBtnGoodsType;
    @BindView(R.id.et_goods_describe)
    EditText mEtGoodsDescribe;
    @BindView(R.id.btn_goods_load)
    Button mBtnGoodsLoad;
    @BindView(R.id.activity_goods_up_load)
    RelativeLayout mActivityGoodsUpLoad;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private PicWindow mPicWindow;//照相和图片的弹出框
    private GoodsUploadAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_up_load);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //初始化适配器
        init();
    }

    private void init() {
        mPicWindow=new PicWindow(this,mListener);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        //设置默认item的增删动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //固定item的大小，设置这选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        //设置适配器
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private PicWindow.Listener mListener=new PicWindow.Listener() {
        @Override
        public void toGallery() {
            //获取相册图片
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(mCropHandler.getCropParams().uri);
            //Intent.ACTION_GET_CONTENT
            Intent intent=CropHelper.buildCropFromGalleryIntent(mCropHandler.getCropParams());
        }

        @Override
        public void toCamera() {
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(mCropHandler.getCropParams().uri);
            //MediaStore.ACTION_IMAGE_CAPTURE
            Intent intent=CropHelper.buildCaptureIntent(mCropHandler.getCropParams().uri);
        }
    };
    //剪裁图片的Handler
    public CropHandler mCropHandler=new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            //图片剪裁完成后
            //把我们剪裁的图片保存到bitmap中,并保存到sd中
            //文件名：用系统当前时间  不重复
            //4393726151119.jpeg
            String fileName=String.valueOf(System.currentTimeMillis());
            //先变成Bitmap
            Bitmap bitmap= ImageUtils.readDownsampledImage(uri.getPath(),1080,1920);
            //将图片存储到sd卡中
            MyFileUtils.saveBitmap(bitmap,fileName);//照完相  将图片保存到外置sd卡中
            //展示出来
            ImageItem imageItem=new ImageItem();
            imageItem.setBitmap(bitmap);//放置bitmap图片
            imageItem.setImagePath(fileName+".JPEG");//放置文件名
            mAdapter.add(imageItem);//添加一个上传商品

        }

        @Override
        public void onCropCancel() {

        }

        @Override
        public void onCropFailed(String message) {

        }

        @Override
        public CropParams getCropParams() {
            return null;
        }

        @Override
        public Activity getContext() {
            return null;
        }
    };
}
