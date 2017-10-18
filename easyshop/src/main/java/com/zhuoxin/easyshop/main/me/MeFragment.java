package com.zhuoxin.easyshop.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;
import com.zhuoxin.easyshop.R;
import com.zhuoxin.easyshop.commons.ActivityUtils;
import com.zhuoxin.easyshop.main.me.goodsupdate.GoodsUploadActivity;
import com.zhuoxin.easyshop.main.me.persongoods.PersonGoodsActivity;
import com.zhuoxin.easyshop.main.me.personinfo.PersonActivity;
import com.zhuoxin.easyshop.model.AvaterLoadOptions;
import com.zhuoxin.easyshop.model.CachePreferences;
import com.zhuoxin.easyshop.network.EasyShopApi;
import com.zhuoxin.easyshop.user.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MeFragment extends Fragment {
    @BindView(R.id.iv_user_head)//用户头像
    CircularImageView iv_UserHead;
    @BindView(R.id.tv_login)//"登录|注册"
    TextView tv_Login;
    @BindView(R.id.tv_person_info)//个人信息
    TextView tv_PersonInfo;
    @BindView(R.id.tv_person_goods)//我的商品
    TextView tv_PersonGoods;
    @BindView(R.id.tv_person_upload)//商品上传
    TextView tv_PersonUpload;
    Unbinder unbinder;
    private ActivityUtils activityUtils;//activity登录或者存储的工具类
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);
        activityUtils=new ActivityUtils(this);//初始化ActivityUtils
        return view;
    }
    @Override
    public void onStart() {//以来就执行的方法
        super.onStart();
        if (CachePreferences.getUser().getName()==null){//没有登录直接返回
            return;
        }
        //设置昵称
        if (CachePreferences.getUser().getNick_name()==null){//如果昵称为空
            tv_Login.setText("请输入昵称");
        }else{//如果有昵称
            tv_Login.setText(CachePreferences.getUser().getNick_name());
        }
        //设置头像
        ImageLoader.getInstance().displayImage(EasyShopApi.IMAGE_URL+CachePreferences.getUser().getHead_Image(),iv_UserHead, AvaterLoadOptions.build());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @OnClick({R.id.iv_user_head, R.id.tv_login, R.id.tv_person_info, R.id.tv_person_goods, R.id.tv_person_upload})
    public void onViewClicked(View view) {
        if (CachePreferences.getUser().getName()==null){//如果没有登录过进入登陆页面，如果登录过进入个人信息页面
            activityUtils.startActivity(LoginActivity.class);
            return;
        }
        switch (view.getId()) {
            //点击头像和"登录|注册"跳转到登录页面
            case R.id.iv_user_head:
            case R.id.tv_login:
            case R.id.tv_person_info://进入个人信息页面
                activityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods://进入个人商品页面
                activityUtils.startActivity(PersonGoodsActivity.class);
                break;
            case R.id.tv_person_upload://进入商品上传页面
                activityUtils.startActivity(GoodsUploadActivity.class);
                break;
        }
    }
}