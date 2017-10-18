package com.zhuoxin.easyshop.main.me.goodsupdate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.commons.ImageUtils;
import com.zhuoxin.easyshop.commons.MyFileUtils;
import com.zhuoxin.easyshop.components.PicWindow;
import com.zhuoxin.easyshop.components.ProgressDialogFragment;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.GoodsUpLoad;
import com.zhuoxin.easyshop.model.ImageItem;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * toolbar--相片--RecyclerView
 */
public class GoodsUploadActivity extends MvpActivity<GoodsUploadView, GoodsUploadPresenter> implements GoodsUploadView {

    @BindView(R.id.tv_goods_delete)
    TextView tv_goodsDelete;//toolbar右上角删除按钮
    @BindView(R.id.toolbar)
    Toolbar toolbar;//工具条
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;//列表视图
    @BindView(R.id.et_goods_name)
    EditText et_goodsName;//商品名称
    @BindView(R.id.et_goods_price)
    EditText et_goodsPrice;//商品
    @BindView(R.id.tv_goods_type)
    TextView tv_goodsType;//商品类型
    @BindView(R.id.btn_goods_type)
    Button btn_goodsType;//类型选择按钮
    @BindView(R.id.et_goods_describe)
    EditText et_goodsDescribe;//商品描述
    @BindView(R.id.btn_goods_load)
    Button btn_goodsLoad;//上传按钮
    //定义数据
    private final String[] goods_type={"家用","电子","服饰","玩具","图书","礼品","其他"};
    //定义商品的类型
    private final String[] goods_type_num={"household","electron","dress","toy","book","gift","other"};
    private ActivityUtils activityUtils;//Activity的工具类
    private String str_goods_name;//获取商品名
    private String str_goods_price;//商品价格
    private String str_goods_type=goods_type_num[0];//商品类型默认家用
    private String str_goods_describe;//商品描述
    //模式：普通 模式
    private static final int MODE_DONE=1;//点击删除的模式 没有ChechBox的模式
    //模式：删除 模式
    private static final int MODE_DELETE=2;//长按  有CheckBox的模式
    //存储当前模式
    private int title_mode=MODE_DONE;//一来默认是没有ChechBox的模式
    private ArrayList<ImageItem> list=new ArrayList<>();//上传商品item的集合
    private GoodsUploadAdapter adapter;//上传商品的适配器
    private PicWindow picWindow;//照相和图片的弹出框
    private ProgressDialogFragment progressDialogFragment;//中间圆形的progressbar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_up_load);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //给Toolbar设置返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //初始化适配器
        initView();
    }

    private void initView() {
        //弹出框
        picWindow=new PicWindow(this,listener);
        //RecyclerView设置
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));//4列的网格视图
        //设置默认item的增删动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //固定item的大小，设置这选项可以提高性能
        recyclerView.setHasFixedSize(true);
        //构建适配器
        adapter=new GoodsUploadAdapter(this,list);
        adapter.setListener(itemClickListener);
        //设置适配器
        recyclerView.setAdapter(adapter);
        //给编辑框设置监听
        et_goodsName.addTextChangedListener(textWatcher);//商品名称监听
        et_goodsPrice.addTextChangedListener(textWatcher);//商品价格监听
        et_goodsDescribe.addTextChangedListener(textWatcher);//商品描述监听

    }
    //给返回键家监听器
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private PicWindow.Listener listener=new PicWindow.Listener() {
        @Override
        public void toGallery() {//获取相册图片
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //Intent.ACTION_GET_CONTENT
            Intent intent=CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            //启动相册组件
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }

        @Override
        public void toCamera() {//拍照获取图片
            //清理剪裁图片的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //MediaStore.ACTION_IMAGE_CAPTURE
            Intent intent=CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            //启动相机组件
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);
        }
    };
    private GoodsUploadAdapter.OnItemClickListener itemClickListener=new GoodsUploadAdapter.OnItemClickListener() {
        @Override
        public void onAddClicked() {//点击加号图片弹出PopupWindow
            if (picWindow!=null&&picWindow.isShowing()){//PopupWindow显示点击加号  消失
                picWindow.dismiss();
            }else if(picWindow!=null){
                picWindow.show();////PopupWindow显示点击加号  显示
            }
        }

        @Override
        public void onPhotoClicked(ImageItem imageItem) {//点击商品图片进入图片详情页
            //点击商品上传图片跳转到商品详情页面
            Intent intent=new Intent(GoodsUploadActivity.this,GoodsUploadImageShowActivity.class);
            intent.putExtra("images",imageItem.getBitmap());//Bitmap实现了Parcelable的序列化接口
            startActivity(intent);//启动意图
        }

        @Override
        public void onLongClicked() {//长按 显示删除按钮  (适配器里显示CheckBox)
            //模式变成可删除模式  出现删除按钮
            title_mode=MODE_DELETE;
            //删除按钮显示
            tv_goodsDelete.setVisibility(View.VISIBLE);
        }
    };
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {//监听编辑框改变之后的操作
            str_goods_name=et_goodsName.getText().toString();//获取商品名称
            str_goods_price=et_goodsPrice.getText().toString();//获取商品的价格
            str_goods_describe=et_goodsDescribe.getText().toString();//获取商品描述
            //所有的编辑框都有内容时才可以上传
            boolean can_upload=!(TextUtils.isEmpty(str_goods_name)||TextUtils.isEmpty(str_goods_price)||TextUtils.isEmpty(str_goods_describe));
            btn_goodsLoad.setEnabled(can_upload);
        }
    };
    @NonNull
    @Override
    public GoodsUploadPresenter createPresenter() {//构建GoodsUploadPresenter实例
        return new GoodsUploadPresenter();
    }
    //视图接口的实现  即是业务层处理完之后UI更新
    @Override
    public void showPrb() {//显示progressbar
        if (progressDialogFragment==null){
            progressDialogFragment=new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()){
          return;
        }
        progressDialogFragment.show(getSupportFragmentManager(),"fragment_dialog");
    }

    @Override
    public void hidePrb() {
        progressDialogFragment.dismiss();
    }
    @Override
    public void upLoadSuccess() {
        finish();
    }
    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    @OnClick({R.id.tv_goods_delete, R.id.btn_goods_type, R.id.btn_goods_load})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods_delete://点击删除按钮 把打了勾图片删除
                ArrayList<ImageItem> lists=adapter.getList();//适配器里所有的ImageItem
                int num=lists.size();//集合中元素个数
                for (int i=num-1;i>=0;i-- ){//遍历元素
                    if (lists.get(i).isCheck()){
                        //删除缓存文件夹中的图片
                        MyFileUtils.delFile(lists.get(i).getImagePath());//根据文件路径删除图片
                        lists.remove(i);//根据下标移除元素 移除要删除的图片
                    }
                }
                list=lists;//放置不删除的图片
                changeModeActivity();
                break;
            case R.id.btn_goods_type://点击商品显示商品类型
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle(R.string.goods_type_check);
                builder.setItems(goods_type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//which指的是item的位置从0开始
                        //拿到商品类型放到对应控件上
                        tv_goodsType.setText(goods_type[which]);//{"家用","电子","服饰","玩具","图书","礼品","其他"};
                        //拿到商品类型的英文描述(用于上传)
                        str_goods_type=goods_type_num[which];//设置要上传商品的类型
                    }
                });
                builder.create().show();//显示Dialog

                break;
            case R.id.btn_goods_load://上传商品
                if (adapter.getSize()==0){
                    activityUtils.showToast("最少有一张商品图片");
                    return;
                }
                presenter.upLoad(setGoodsInfo(),list);
                break;
        }
    }
    //点击删除 按钮
    private void changeModeActivity() {
        //判断 当前模式
        if (adapter.getMode()==GoodsUploadAdapter.MODE_MULTI_SELECT){//显示checkBox的状态
            tv_goodsDelete.setVisibility(View.GONE);//删除按钮修饰
            //更改模式
            title_mode=MODE_DONE;//不显示CheckBox的模式
            //更改CheckBox中的模式
            adapter.changeMode(GoodsUploadAdapter.MODE_NORMAL);//不显示CheckBox的模式
            //可写可不写
            for (int i=0;i<adapter.getList().size();i++){//遍历的是删除过留下来的元素
                adapter.getList().get(i).setCheck(false);
            }
        }
    }

    //返回要上传的实体类 包括商品：名称 价格 描述 分类 发布者
       /*
            {
            "description": "诚信商家，非诚勿扰",     //商品描述
            "master": "android",                    //商品发布者
            "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
            "price": "88",                          //商品价格
            "type": "gift"                          //商品类型
            }
         */
    private GoodsUpLoad setGoodsInfo() {
        GoodsUpLoad goodsUpLoad=new GoodsUpLoad();
        goodsUpLoad.setName(str_goods_name);//上传商品名称
        goodsUpLoad.setPrice(str_goods_price);//上传商品价格
        goodsUpLoad.setDescription(str_goods_describe);//上传商品描述
        goodsUpLoad.setType(str_goods_type);//上传商品类型
        goodsUpLoad.setMaster(CachePreferences.getUser().getName());//撒谎上传发布者
        return goodsUpLoad;
    }

    //剪裁图片的Handler
    public CropHandler cropHandler=new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {//图片剪裁完成后
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
            adapter.add(imageItem);//添加一个上传商品
            adapter.notifyData();//更新适配器
        }
        @Override
        public void onCropCancel() {//剪裁取消

        }

        @Override
        public void onCropFailed(String message) {//剪裁失败

        }

        @Override
        public CropParams getCropParams() {//设置剪裁参数
            CropParams params=new CropParams();
            //设置剪裁框
            params.aspectX=400;
            params.aspectY=400;
            return params;
        }

        @Override
        public Activity getContext() {
            return GoodsUploadActivity.this;
        }
    };
    //获得图片后执行  data存储了我们的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //将图片data交给cropHandler处理
        CropHelper.handleResult(cropHandler,requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {
        if (title_mode==MODE_DONE){//无CheckBox的状态
            //删除缓存
            deleteCache();
            finish();
        }else if (title_mode==MODE_DELETE){//当前状态是 有CheckBox的状态
            changeModeActivity();
        }

    }
    private void deleteCache() {
        for (int i=0;i<adapter.getList().size();i++){
            MyFileUtils.delFile(adapter.getList().get(i).getImagePath());//获取每一张图片 删除
        }
    }
}
