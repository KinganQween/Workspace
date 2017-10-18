package com.zhuoxin.easyshop.main.me.personinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.feicuiedu.apphx.model.HxUserManager;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.components.PicWindow;
import com.zhuoxin.easyshop.components.ProgressDialogFragment;
import com.zhuoxin.easyshop.main.MainActivity;
import com.zhuoxin.easyshop.model.AvaterLoadOptions;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.model.ItemShow;
import com.zhuoxin.easyshop.model.User;
import com.zhuoxin.easyshop.network.EasyShopApi;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends MvpActivity<PersonView, PersonPresenter> implements PersonView {//View层展示数据
    @BindView(R.id.toolbar)//工具条显示Menu菜单
    Toolbar toolbar;
    @BindView(R.id.iv_user_head)
    CircularImageView iv_userHead;//用户头像
    @BindView(R.id.listView)//显示用户名，昵称，环信id
    ListView listView;
    @BindView(R.id.btn_login_out)//退出登录按钮
    Button btn_loginOut;
    private ActivityUtils activityUtils;//存储activity`,实现activity吐司和跳转的
    private ProgressDialogFragment progressDialogFragment;//圆形的progressbar
    private List<ItemShow> list=new ArrayList<>();//显示ListView的item的集合
    private PicWindow picWindow;//popupWindow弹出框
    private PersonAdapter adapter;//适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //设置toolbar的返回键
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter=new PersonAdapter(list);//构建适配器
        listView.setAdapter(adapter);//设置适配器
        listView.setOnItemClickListener(onItemClickListener);//给ListView的item添加监听器
        //一来就获取头像
        updateAvatar(CachePreferences.getUser().getHead_Image());
    }
    //加载ListView的数据

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();//清空适配器集合
        init();//初始化ListView适配器中集合
    }
    private void init() {
        User user=CachePreferences.getUser();//获取之前存储的User
        list.add(new ItemShow("用户名",user.getName()));
        list.add(new ItemShow("昵称",user.getNick_name()));
        list.add(new ItemShow("环信ID",user.getHx_Id()));
        adapter.notifyDataSetChanged();//更新适配器
    }
    //返回键的监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();//销毁当前页面
        }
        return super.onOptionsItemSelected(item);
    }
    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0://用户名  不能更改
                    activityUtils.showToast(R.string.username_update);//用户名作为登录名不可以修改~
                    break;
                case 1://昵称
                    activityUtils.startActivity(NickNameActivity.class);//跳转到修改昵称页面
                    break;
                case 2://环信ID 不能修改
                    activityUtils.showToast(R.string.id_update);//环信ID不可以修改~
                    break;
            }
        }
    };
    @NonNull
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }
    @Override
    public void showPrb() {
        if (progressDialogFragment==null){
            progressDialogFragment=new ProgressDialogFragment();
        }
        if (progressDialogFragment.isVisible()){
            return;
        }
        progressDialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }
    @Override
    public void hidePrb() {
        progressDialogFragment.dismiss();
    }
    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }
    //根据路径 更新头像
    @Override
    public void updateAvatar(String url) {
        //加载头像
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+url,iv_userHead, AvaterLoadOptions.build());
    }
    @OnClick({R.id.iv_user_head, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head://点击头像 选择图库 或者照相
                if (picWindow==null){
                    picWindow=new PicWindow(this,listener);
                }
                if (picWindow.isShowing()){//如果显示了PicWindow点击销毁PicWindow
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();
                break;
            case R.id.btn_login_out://退出登录
                CachePreferences.clearAllData();//清空SharedPreferences里存储的登录信息
                Intent intent=new Intent(this, MainActivity.class);//跳转到MainAcitivity中
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//清空老的任务栈  将MainAcitivity放到新的任务栈中
                startActivity(intent);
                //退出环信相关
                HxUserManager.getInstance().asyncLogout();//环信退出登录
                //登出关掉通知栏中的环信通知
                EaseUI.getInstance().getNotifier().reset();
                break;
        }
    }
    private PicWindow.Listener listener=new PicWindow.Listener() {
        @Override
        public void toGallery() {//相册中  需要剪裁
            //从相册中清空剪裁的缓存
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            //构建相册一意图  Intent.ACTION_GET_CONTENT
            Intent intent=CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);//127认为是相册
        }
        @Override
        public void toCamera() {//相机中
            //从相册中清空剪裁的缓存  MediaStore.ACTION_IMAGE_CAPTURE  uri指的是我们照完相的图片地址
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent=CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);//128认为是相机的
        }
    };

    //图片剪裁的handler
    private CropHandler cropHandler=new CropHandler() {
        //图片剪裁结束后
        //通过uri拿到图片
        @Override
        public void onPhotoCropped(Uri uri) {
            File file=new File(uri.getPath());//此为图片文件
            presenter.updateAvatar(file);//上传图片文件
        }
        @Override
        public void onCropCancel() {
            //停止剪裁触发
        }

        @Override
        public void onCropFailed(String message) {
            //剪裁失败
        }

        @Override
        public CropParams getCropParams() {
            //设置剪裁参数
            CropParams cropParams=new CropParams();
            //400*400的正方形 剪裁框
            cropParams.aspectX=400;
            cropParams.aspectY=400;
            return cropParams;
        }

        @Override
        public Activity getContext() {//底层需要上下文实例
            return PersonActivity.this;
        }
    };
    //从相册中取得图片或者照完相 会回调onActivityResult

    /**
     *
     * @param requestCode  127相册  128相机
     * @param resultCode
     * @param data  图片数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropHelper.handleResult(cropHandler,requestCode,resultCode,data);//交给cropHandler去处理
    }
}
