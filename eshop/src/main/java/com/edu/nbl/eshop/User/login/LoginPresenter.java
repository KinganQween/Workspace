package com.edu.nbl.eshop.User.login;

import android.util.Log;

import com.edu.nbl.eshop.commons.ConvertUser;
import com.edu.nbl.eshop.model.CachePreferences;
import com.edu.nbl.eshop.model.User;
import com.edu.nbl.eshop.model.UserResult;
import com.edu.nbl.eshop.network.EasyShopClient;
import com.edu.nbl.eshop.network.UICallBack;
import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Administrator on 17-9-25.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{
private Call mCall;
    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (mCall!=null){
            mCall.cancel();//销毁Call模型

        }
        EventBus.getDefault().unregister(this);
    }
    //执行登录请求
    public void login(final String username, String password){
        getView().showPrb();//显示progress
        mCall= EasyShopClient.getInstance().login(username,password);//发送登录请求，传送用户名和密码参数，
       mCall.enqueue(new UICallBack() {
           @Override
           public void onFailureUI(Call call, IOException e) {
               getView().hidePrb();//隐藏progress
               getView().showMsg(e.getMessage());//吐司错误信息
           }

           @Override
           public void onResponseUI(Call call, String body) {
            getView().hidePrb();//隐藏progressbar
               Log.d("Login","body="+body);
               UserResult mResult=new  Gson().fromJson(body,UserResult.class);
               Log.d("Login","mResult="+mResult);
               if (mResult.getCode()==1){//登录成功吐司
                   getView().showMsg("登录成功");//吐司登陆成功
                   /*
                    "username": "xc01",
                    "other": "/images/35C69D35E4164D19B4278DC45FDCAF45/2D505F81BB.jpg",
                    "nickname": "666",
                    "name": "yt0186129caad847a98a71189fda4f2300",
                    "uuid": "35C69D35E4164D19B4278DC45FDCAF45",
                    "password": "123456"
                     */
                   User mUser=mResult.getData();//获取用户信息
                   CachePreferences.setUser(mUser);//存储用户信息

                   //环信登录
                   EaseUser mEaseUser= ConvertUser.convert(mUser);
                   HxUserManager.getInstance().asyncLogin(mEaseUser,mUser.getPassword());
               }else if (mResult.getCode()==2){//登录失败
                   getView().hidePrb();//隐藏progressbar
                   getView().loginFailed();//执行登录失败UI
               }else {
                   getView().hidePrb();//隐藏progressbaret
                   getView().showMsg("未知错误");
               }
           }
       });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        if (event.type!= HxEventType.LOGIN)return;
        //表示登录成功
        getView().showMsg("登录成功");
        getView().loginSuccess();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event){
        if (event.type!=HxEventType.LOGIN)return;
        getView().showMsg("环信地址不对");
    }


}
